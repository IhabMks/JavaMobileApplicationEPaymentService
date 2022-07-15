package com.example.intesy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Client.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_client_TABLE = "CREATE TABLE " +
                Table_list.client_table.TABLE_NAME + " (" +
                Table_list.client_table.id_col + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table_list.client_table.username_col + " TEXT NOT NULL, " +
                Table_list.client_table.phone_col + " TEXT NOT NULL, " +
                Table_list.client_table.client_code_col + " TEXT NOT NULL, " +
                Table_list.client_table.password_col + " TEXT NOT NULL, " +
                Table_list.client_table.account_type_col + " TEXT NOT NULL, " +
                Table_list.client_table.overall_payment_col + " INTEGER NOT NULL, " +
                Table_list.client_table.adsl_last_paid_day_col + " TEXT NOT NULL, " +
                Table_list.client_table.line_last_paid_day_col + " TEXT NOT NULL, " +
                Table_list.client_table.adsl_day_col + " INTEGER NOT NULL, " +
                Table_list.client_table.line_day_col + " INTEGER NOT NULL, " +
                Table_list.client_table.account_confirmation_col + " TEXT NOT NULL, " +
                Table_list.client_table.subscription_date_col + " TEXT NOT NULL" + ")";

        final String SQL_CREATE_BILLING_HISTORY_TABLE = "CREATE TABLE " +
                Table_list.billing_history_table.TABLE_NAME + " (" +
                Table_list.billing_history_table.id_col + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table_list.billing_history_table.phone_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.payment_amount_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.payment_date_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.option_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.method_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.payment_status_col + " TEXT NOT NULL, " +
                Table_list.billing_history_table.timestamp_col + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")";


        db.execSQL(SQL_CREATE_client_TABLE);
        db.execSQL(SQL_CREATE_BILLING_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_list.client_table.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table_list.billing_history_table.TABLE_NAME);
        onCreate(db);
    }

    /*------------------------------------------Client_table------------------------------------------*/
    public boolean insert_client(String username, String number, String client_code, String password, String account_type) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Table_list.client_table.username_col, username);
        cv.put(Table_list.client_table.phone_col, number);
        cv.put(Table_list.client_table.client_code_col, client_code);
        cv.put(Table_list.client_table.password_col, password);
        cv.put(Table_list.client_table.account_type_col, account_type);
        cv.put(Table_list.client_table.overall_payment_col, 0);
        cv.put(Table_list.client_table.adsl_last_paid_day_col, "");
        cv.put(Table_list.client_table.line_last_paid_day_col, "");
        cv.put(Table_list.client_table.adsl_day_col, 0);
        cv.put(Table_list.client_table.line_day_col, 0);
        cv.put(Table_list.client_table.subscription_date_col, currentDate);
        cv.put(Table_list.client_table.account_confirmation_col, "false");

        long ins = db.insert(Table_list.client_table.TABLE_NAME, null, cv);
        return ins != -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean update_client(String phone, String option) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME + " WHERE "
                + Table_list.client_table.phone_col + "=?", new String[]{phone});
        cursor.moveToFirst();
        int overall_payment = cursor.getInt(cursor.getColumnIndex(Table_list.client_table.overall_payment_col));

        ContentValues cv = new ContentValues();
        cv.put(Table_list.client_table.overall_payment_col, overall_payment + 1);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (option.equals("ADSL")) {
            String adsl_last_payed = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            LocalDate d1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(adsl_last_payed, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            long diffDays = diff.toDays() + 30;

            cv.put(Table_list.client_table.adsl_last_paid_day_col, adsl_last_payed);
            cv.put(Table_list.client_table.adsl_day_col, (int) diffDays);
        }
        if (option.equals("LINE")) {
            String line_last_payed = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            LocalDate d1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(line_last_payed, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            long diffDays = diff.toDays() + 90;

            cv.put(Table_list.client_table.line_last_paid_day_col, line_last_payed);
            cv.put(Table_list.client_table.line_day_col, (int) diffDays);
        }


        db.update(Table_list.client_table.TABLE_NAME, cv, Table_list.client_table.phone_col + " = ?", new String[]{phone});
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean update_adsl_days_remaining(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME + " WHERE "
                + Table_list.client_table.phone_col + "=?", new String[]{phone});
        cursor.moveToFirst();

        ContentValues cv = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String adsl_last_payed = cursor.getString(cursor.getColumnIndex(Table_list.client_table.adsl_last_paid_day_col));

        LocalDate adsl_1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate adsl_2 = LocalDate.parse(adsl_last_payed, DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff_1 = Duration.between(adsl_1.atStartOfDay(), adsl_2.atStartOfDay());
        long adsl_diff = diff_1.toDays() + 30;

        cv.put(Table_list.client_table.adsl_day_col, (int) adsl_diff);

        db.update(Table_list.client_table.TABLE_NAME, cv, Table_list.client_table.phone_col + " = ?", new String[]{phone});
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean update_line_days_remaining(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME + " WHERE "
                + Table_list.client_table.phone_col + "=?", new String[]{phone});
        cursor.moveToFirst();

        ContentValues cv = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String line_last_payed = cursor.getString(cursor.getColumnIndex(Table_list.client_table.line_last_paid_day_col));

        LocalDate line_1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate line_2 = LocalDate.parse(line_last_payed, DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff_2 = Duration.between(line_1.atStartOfDay(), line_2.atStartOfDay());
        long line_diff = diff_2.toDays() + 90;

        cv.put(Table_list.client_table.line_day_col, (int) line_diff);


        db.update(Table_list.client_table.TABLE_NAME, cv, Table_list.client_table.phone_col + " = ?", new String[]{phone});
        return true;
    }


    public boolean check_username_existence(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME +
                " WHERE " + Table_list.client_table.username_col + "=?", new String[]{username});
        return cursor.getCount() <= 0;
    }

    public boolean check_phone_existence(String number) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME +
                " WHERE " + Table_list.client_table.phone_col + "=?", new String[]{number});
        return cursor.getCount() <= 0;
    }

    public boolean check_client_code_existence(String client_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME +
                " WHERE " + Table_list.client_table.client_code_col + "=?", new String[]{client_code});
        return cursor.getCount() <= 0;
    }


    public boolean check_credential(String number, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME +
                " WHERE " + Table_list.client_table.phone_col + "=? AND " +
                Table_list.client_table.password_col + "=?", new String[]{number, password});
        return cursor.getCount() > 0;
    }

    public Cursor get_credential(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_list.client_table.TABLE_NAME + " WHERE "
                + Table_list.client_table.phone_col + "=?", new String[]{phone});
        return cursor;
    }
    /*------------------------------------------Client_table------------------------------------------*/




    /*------------------------------------------History_table------------------------------------------*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insert_history(String phone, String payment_amount, String option, String method, String payment_status_col) {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv_history = new ContentValues();
        cv_history.put(Table_list.billing_history_table.phone_col, phone);
        cv_history.put(Table_list.billing_history_table.payment_amount_col, payment_amount);
        cv_history.put(Table_list.billing_history_table.payment_date_col, currentDate);
        cv_history.put(Table_list.billing_history_table.option_col, option);
        cv_history.put(Table_list.billing_history_table.method_col, method);
        cv_history.put(Table_list.billing_history_table.payment_status_col, payment_status_col);

        update_client(phone, option);

        long ins = db.insert(Table_list.billing_history_table.TABLE_NAME, null, cv_history);
        return ins != -1;
    }

    public Cursor get_history_data(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Table_list.billing_history_table.TABLE_NAME + " WHERE "
                + Table_list.billing_history_table.phone_col + "=?" + " ORDER BY "
                + Table_list.billing_history_table.timestamp_col + " DESC", new String[]{phone});

    }
    /*------------------------------------------History_table------------------------------------------*/
}
