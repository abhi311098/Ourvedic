package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AP_Current_Order extends AppCompatActivity {


    ArrayList current_order_ap_al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__ordre__ap);
        current_order_ap_al = new ArrayList();
    }
}