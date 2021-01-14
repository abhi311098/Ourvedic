package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AP_Current_Order extends AppCompatActivity {

    ArrayList<order_details> current_order_ap_al;
    DatabaseReference adminCurrentOrderRef = FirebaseDatabase.getInstance().getReference("Admin").child("current_orders");

    String orderId, name, email, itemIds, delivery_address, mob, mode_of_payment, order_date_time, delivered_date_time;
    int final_amount;
    ListView lv_current_orders;
    ImageView nothing_to_show;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__ordre__ap);
        current_order_ap_al = new ArrayList<>();
        lv_current_orders = findViewById(R.id.lv_current_orders);
        nothing_to_show = findViewById(R.id.nothing_to_show);

        progressDialog = new ProgressDialog(AP_Current_Order.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            nothing_to_show.setVisibility(View.GONE);
            adminCurrentOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss : snapshot.getChildren()){
                            name = dss.child("name").getValue(String.class);
                            email = dss.child("email").getValue(String.class);
                            itemIds = dss.child("itemIds").getValue(String.class);
                            delivery_address = dss.child("delivery_address").getValue(String.class);
                            mob = dss.child("mobile").getValue(String.class);
                            try{
                                final_amount = dss.child("final_amount").getValue(Integer.class);
                            }catch (Exception e){nothing_to_show.setVisibility(View.VISIBLE);}

                            mode_of_payment = dss.child("mode_of_payment").getValue(String.class);
                            order_date_time = dss.child("order_date_time").getValue(String.class);
                            delivered_date_time = dss.child("delivered_date_time").getValue(String.class);
                            order_details o = new order_details(name , email, itemIds, delivery_address, mob, final_amount, mode_of_payment, order_date_time, delivered_date_time);
                            current_order_ap_al.add(o);
                            progressDialog.dismiss();
                            AP_CurrentOrderAdapter ap_currentOrderAdapter = new AP_CurrentOrderAdapter(AP_Current_Order.this, current_order_ap_al);
                            lv_current_orders.setAdapter(ap_currentOrderAdapter);
                        }
                    }
                    else{
                        nothing_to_show.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}