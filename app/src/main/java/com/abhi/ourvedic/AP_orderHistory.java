package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AP_orderHistory extends AppCompatActivity {

    ListView lv_ap_orderHistory;
    ArrayList <order_details> orderHistoryAlist;
    String orderId, name, email, itemIds, delivery_address, mob, mode_of_payment, order_date_time, delivered_date_time;
    int final_amount;
    ImageView nothing_to_show;

    DatabaseReference adminCurrentOrderRef = FirebaseDatabase.getInstance().getReference("Admin").child("order_history");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_order_history);
        orderHistoryAlist = new ArrayList<>();
        nothing_to_show = findViewById(R.id.nothing_to_show);

        adminCurrentOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nothing_to_show.setVisibility(View.GONE);
                    for(DataSnapshot dss: snapshot.getChildren()){
                        name = dss.child("name").getValue(String.class);
                        email = dss.child("email").getValue(String.class);
                        itemIds = dss.child("itemIds").getValue(String.class);
                        delivery_address = dss.child("delivery_address").getValue(String.class);
                        mob = dss.child("mobile").getValue(String.class);
                        try {final_amount = dss.child("final_amount").getValue(Integer.class);}
                        catch (Exception e){nothing_to_show.setVisibility(View.VISIBLE);}
                        mode_of_payment = dss.child("mode_of_payment").getValue(String.class);
                        order_date_time = dss.child("order_date_time").getValue(String.class);
                        delivered_date_time = dss.child("delivered_date_time").getValue(String.class);

                        order_details o = new order_details(name , email, itemIds, delivery_address, mob, final_amount, mode_of_payment, order_date_time, delivered_date_time);
                        orderHistoryAlist.add(o);

                        lv_ap_orderHistory = findViewById(R.id.lv_ap_orderHistory);
                        AP_orderHistoryAdapter orderHistoryAdapter = new AP_orderHistoryAdapter(AP_orderHistory.this, orderHistoryAlist);
                        lv_ap_orderHistory.setAdapter(orderHistoryAdapter);
                    }
                }
                else {
                    nothing_to_show.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}