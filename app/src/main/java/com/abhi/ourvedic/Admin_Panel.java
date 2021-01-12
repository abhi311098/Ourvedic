package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Admin_Panel extends AppCompatActivity {

    TextView tv_admin_panel;
    CardView current_orders;
    CardView item_settings;
    CardView ordersHistory;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminpannel_logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_adminpanel_tv) {
            finish();
            return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__panel);


        tv_admin_panel = findViewById(R.id.tv_admin_panel);
        current_orders = findViewById(R.id.current_orders);
        current_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_Panel.this, AP_Current_Order.class);
                startActivity(i);
            }
        });
        item_settings = findViewById(R.id.item_settings);
        item_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Panel.this, AP_ItemSettings.class));
            }
        });
        ordersHistory = findViewById(R.id.ordersHistory);
        ordersHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Panel.this, AP_orderHistory.class));
            }
        });
    }
}