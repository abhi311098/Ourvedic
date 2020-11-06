package com.abhi.ourvedic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String emailaddress = getIntent().getStringExtra("email");
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailaddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Log.d("reset", "Email sent.");
                Intent intent = new Intent(ResetPassword.this, LoginPage.class);
                startActivity(intent);
                finish();
                Toast.makeText(ResetPassword.this, "Check Your Email", Toast.LENGTH_LONG).show();
            }
        }
    });
}
}
