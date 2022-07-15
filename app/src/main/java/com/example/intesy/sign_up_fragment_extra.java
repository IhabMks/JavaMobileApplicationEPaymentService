package com.example.intesy;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.Executor;

public class sign_up_fragment_extra extends Fragment {
    private ImageButton back_button;
    private TextView steps_mod_2;
    private TextView wrong_code_popup;
    private LinearLayout already_user_ll_extra;
    private EditText v_c_field;
    //private FirebaseAuth mAuth;
    //private String CodeSent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment_extra, container, false);
        /*-----------------------Feature's declaration-----------------------*/
        //mAuth=FirebaseAuth.getInstance();
        wrong_code_popup = v.findViewById(R.id.wrong_code_popup);
        v_c_field = v.findViewById(R.id.v_c_field);
        back_button = v.findViewById(R.id.back_button);


        /*-----------------------Already_user_LinearLayout_blink_Animation-----------------------*/
        already_user_ll_extra = v.findViewById(R.id.already_user_ll_extra);
        Animation user_ll_blink = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
        already_user_ll_extra.startAnimation(user_ll_blink);
        /*-----------------------Already_user_LinearLayout_blink_Animation-----------------------*/


        /*-----------------------phase_modifier-----------------------*/
        steps_mod_2 = v.findViewById(R.id.steps_mod_2);
        String steps_mod_2_string = "<b>2</b><small><sub>/<b>2</b></sub></small><br><small><small><small><b>STEPS</b></small></small></small>";
        steps_mod_2.setText(Html.fromHtml(steps_mod_2_string));
        /*-----------------------phase_modifier-----------------------*/




        /*-----------------------Button action-----------------------*/
        v.findViewById(R.id.submit_button_extra_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CodeSent=getArguments().getString("verification_code");
                verifySignInCode();*/

                if (v_c_field.getText().toString().equals("123456")) {
                    wrong_code_popup.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Access granted", Toast.LENGTH_SHORT).show();
                } else {
                    wrong_code_popup.setVisibility(View.VISIBLE);
                }
            }
        });

        v.findViewById(R.id.login_button_extra_frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new login_fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });


        v.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new home_fragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });
        /*-----------------------Button action-----------------------*/
        return v;
    }
}


//  /*private void verifySignInCode(){
//      /*-----------------------Getting/Checking the verification code entered by the user-----------------------*/
//      /*String code = v_c_field.getText().toString().trim();
//      if (code.isEmpty()) {
//          v_c_field.setError("Verification code is required");
//          v_c_field.requestFocus();
//          Toast.makeText(getActivity(),
//                  "Verification code is required", Toast.LENGTH_LONG).show();
//          return;
//      }
//      /*-----------------------Getting/Checking the verification code entered by the user-----------------------*/
//
//      /*-----------------------creating a PhoneAuthCredential object using the verification code entered by the user and the verification
//                               ID/Code that was passed to the onCodeSent(see previous fragment class)-----------------------*/
//      /*PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeSent, code);**/
//      /*-----------------------Calling signInWithPhoneAuthCredential's method with credential-----------------------*/
//      /*signInWithPhoneAuthCredential(credential);
//  }**/
//
//  /*-----------------------Checking if the verification code entered by the user is correct or not-----------------------*/
//  /*void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//      mAuth.signInWithCredential(credential)
//              .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                  @Override
//                  public void onComplete(@NonNull Task<AuthResult> task) {
//                      if (task.isSuccessful()) {
//                          //here you can open new activity, i.e: logged in session
//                          Toast.makeText(getContext(),
//                                  "Login Successful", Toast.LENGTH_LONG).show();
//                      } else {
//                          if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                              Toast.makeText(getActivity(),
//                                      "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
//                              wrong_code_popup.setVisibility(View.INVISIBLE);
//                          }
//                      }
//                  }
//              });
//  }*/