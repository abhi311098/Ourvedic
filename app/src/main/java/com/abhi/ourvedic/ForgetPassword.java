package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    private EditText email;
    private Button forget;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.femail);
        forget = findViewById(R.id.fbutton);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailaddress = email.getText().toString();
                if (emailaddress.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                } else if (!emailaddress.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "not valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ForgetPassword.this, ResetPassword.class);
                    intent.putExtra("email", emailaddress);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}