package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.abhi.ourvedic.model.DataFModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cart").child(user.getUid());
    Button place_order_tv;
    LinkedHashSet<item> item_cart_copy;
    private String TAG = "errorres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        place_order_tv = findViewById(R.id.place_order_tv);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Map<Object, Object> map = (Map) postSnapshot.getValue();
                        if (map != null) {
                            Object itemname1 = map.get("itemname");
                            Object localname1 = map.get("localname");
                            Object itemid1 = map.get("itemid");
                            Object itemprice1 = map.get("itemprice");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        item_cart_copy = new LinkedHashSet<>();
//        item_cart_copy.add(new item(101, "Agarbatti", "Incense stick", R.drawable.h101, 100));
//        item_cart_copy.add(new item(102, "Ghee", "Ghee", R.drawable.h102, 100));
//        item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma", R.drawable.h103, 100));
//        item_cart_copy.add(new item(104, "phool", "Flowers", R.drawable.h104, 100));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, new ArrayList(item_cart_copy));
        ListView cart_item = findViewById(R.id.cart_item);
        cart_item.setAdapter(cartAdapter);

        place_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    startActivity(new Intent(CartActivity.this, Billing_Details.class));
                }
            }
        });

    }

}
