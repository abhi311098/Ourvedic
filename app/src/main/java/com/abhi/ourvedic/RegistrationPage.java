package com.abhi.ourvedic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private ProgressDialog progressDialog;
    private EditText remail, rpassword, rcpassword;
    private Button reg;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String TAG = "registration";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference users = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        init();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog_view);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mAuth = FirebaseAuth.getInstance();

                final String email = remail.getText().toString().trim();
                final String password = rpassword.getText().toString();
                String cpassword = rcpassword.getText().toString();

                if (email.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "not valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cpassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationPage.this, "password and confirm password are not match", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        user = mAuth.getCurrentUser();
                                        DatabaseReference myCartRef = database.getReference("users").child(user.getUid());

                                        writeNewUser(email, password);

                                        SharedPreferences sh = getSharedPreferences("email", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor edit = sh.edit();
                                        edit.putString("email",email);
                                        edit.commit();
                                        Log.d(TAG, "createUserWithEmail:success");
                                        startActivity(new Intent(RegistrationPage.this, MainActivity.class));
                                        finish();
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationPage.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    // ...
                                }
                            });
                }
            }
        });
    }

    private void init() {
        progressDialog = new ProgressDialog(RegistrationPage.this);
        remail = findViewById(R.id.registrationemail);
        rpassword = findViewById(R.id.registrationpassword);
        rcpassword = findViewById(R.id.registrationpconfirmassword);
        reg = findViewById(R.id.registrationbutton2);

    }

    private void writeNewUser(String email, String password) {
        user_details user_details = new user_details(email, password);
        database.getReference("users").child(user.getUid()).child("user_details").setValue(user_details);
    }
}