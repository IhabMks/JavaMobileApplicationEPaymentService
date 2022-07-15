package com.example.intesy;


public class Table_list {

    private Table_list() {}

    public static final class client_table {
        public static final String TABLE_NAME = "client_table";
        public static final String id_col = "ID";
        public static final String username_col = "USERNAME";
        public static final String phone_col = "PHONE";
        public static final String client_code_col = "CLIENT_CODE";
        public static final String password_col = "PASSWORD";
        public static final String account_type_col = "ACCOUNT_TYPE";
        public static final String overall_payment_col = "OVERALL_PAYMENT";
        public static final String adsl_last_paid_day_col = "LAST_PAYED_ADSL";
        public static final String line_last_paid_day_col = "LAST_PAYED_LINE";
        public static final String adsl_day_col = "ADSL_DAYS";
        public static final String line_day_col = "LINE_DAYS";
        public static final String account_confirmation_col = "ACCOUNT_CONFIRMATION";
        public static final String subscription_date_col = "SUB_DATE";
    }

    public static final class billing_history_table {
        public static final String id_col = "ID";
        public static final String TABLE_NAME = "billing_history_table";
        public static final String phone_col = "PHONE";
        public static final String payment_amount_col = "PAYMENT_AMOUNT";
        public static final String payment_date_col = "PAYMENT_DATE";
        public static final String option_col = "OPTION";
        public static final String method_col = "METHOD";
        public static final String payment_status_col = "STATUS";
        public static final String timestamp_col = "TIMESTAMP";

    }

}
