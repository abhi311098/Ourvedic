package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Admin_Panel extends AppCompatActivity {

    TextView tv_admin_panel;
    CardView current_orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);
        tv_admin_panel = findViewById(R.id.tv_admin_panel);
        current_orders = findViewById(R.id.current_orders);
        current_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Panel.this, Current_Ordre_Ap.class);
                startActivity(i);
            }
        });
    }
}