package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_Panel_Login extends AppCompatActivity {

    EditText admin_password, admin_email;
    private final String admin_remail = "000";
    private final String getAdmin_rpassword = "000";
    Button login_as_admin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel__login);
        admin_email = findViewById(R.id.admin_email);
        admin_password = findViewById(R.id.admin_password);
        login_as_admin_btn = findViewById(R.id.login_as_admin_btn);

        login_as_admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(admin_email.getText().toString().length() == 0){
                    Toast.makeText(Admin_Panel_Login.this, "Enter ID", Toast.LENGTH_SHORT).show();
                }
                else if(admin_password.getText().toString().length() == 0){
                    Toast.makeText(Admin_Panel_Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(String.valueOf(admin_email.getText()).equals(admin_remail) && String.valueOf(admin_password.getText()).equals(getAdmin_rpassword)){
                    Intent i = new Intent(Admin_Panel_Login.this, Admin_Panel.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Admin_Panel_Login.this, "Invalid ID or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}