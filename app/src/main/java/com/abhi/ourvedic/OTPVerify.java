package com.abhi.ourvedic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {

    private EditText otp;
    private Button verify;
    private TextView resend;
    private String number;
    private String id;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);
        init();
        number = getIntent().getStringExtra("number");
        mAuth = FirebaseAuth.getInstance();
        sendVerificationcode();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpVerify();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationcode();
            }
        });

    }

    private void sendVerificationcode() {
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long l) {
                resend.setText(""+1/1000);
                resend.setEnabled(false);
            }
            @Override
            public void onFinish() {
                resend.setText("Resend Otp");
                resend.setEnabled(true);
            }
        }.start();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String id, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(id,forceResendingToken);
                        OTPVerify.this.id=id;
                    }
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(OTPVerify.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    private void otpVerify() {

        String otpv = otp.getText().toString();

        if (TextUtils.isEmpty(otpv)) {
            Toast.makeText(this, "Enter Number", Toast.LENGTH_SHORT).show();
        } else if (otpv.length() != 6) {
            Toast.makeText(this, "Enter Correct Number...", Toast.LENGTH_SHORT).show();
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otpv);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void init() {
        otp = findViewById(R.id.otpnumber);
        verify = findViewById(R.id.otpverify);
        resend = findViewById(R.id.resend);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(OTPVerify.this,MainActivity.class));
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTPVerify.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
