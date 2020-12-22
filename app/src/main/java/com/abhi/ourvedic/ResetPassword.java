package com.abhi.ourvedic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    String emailaddress = "";
    SharedPreferences preferences ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        editor = preferences.edit();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ResetPassword.this);
        if (acct != null) {
            emailaddress = acct.getEmail();
            editor.putString("email",emailaddress);
            editor.commit();
            Log.e("errorres","email: "+emailaddress);
        } else {
            emailaddress = getIntent().getStringExtra("email");
            editor.putString("email",emailaddress);
            editor.commit();
            Log.e("errorres","geg: "+emailaddress);
        }
        Log.e("errorres","proof: "+emailaddress);

        FirebaseAuth.getInstance().sendPasswordResetEmail(emailaddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Log.d("reset", "Email sent.");
                editor.clear();
                editor.commit();
                Intent intent = new Intent(ResetPassword.this, LoginPage.class);
                startActivity(intent);
                finish();
                Toast.makeText(ResetPassword.this, "Check Your Email", Toast.LENGTH_LONG).show();
            }
        }
    });
}
}
