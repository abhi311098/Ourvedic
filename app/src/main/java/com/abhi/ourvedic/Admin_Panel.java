package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

public class Admin_Panel extends AppCompatActivity {

    TextView tv_admin_panel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);
        tv_admin_panel = findViewById(R.id.tv_admin_panel);
    }
}