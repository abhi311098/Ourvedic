
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhi.ourvedic.model.DataFModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");

    int total = 0;
    TextView cart_total;
    Button place_order_tv;
    ArrayList<item> item_cart_copy;
    private String TAG = "errorres";
    ImageView emptyCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        cart_total = findViewById(R.id.cart_total);
        place_order_tv = findViewById(R.id.place_order_tv);
        emptyCart = findViewById(R.id.iv_emptyCart);

        item_cart_copy = new ArrayList<>();
        //item_cart_copy.add(new item(101, "Agarbatti", "Incense stick", R.drawable.h101, 100));
        //item_cart_copy.add(new item(102, "Ghee", "Ghee", R.drawable.h102, 100));
        //item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma", R.drawable.h103, 100));
        //item_cart_copy.add(new item(104, "phool", "Flowers", R.drawable.h104, 100));


        myCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    emptyCart.setVisibility(View.GONE);
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        item i = dss.getValue(item.class);
                        item_cart_copy.add(i);
                        total += i.getItem_Price();
                        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, item_cart_copy);
                        ListView cart_item = findViewById(R.id.cart_item);
                        cart_item.setAdapter(cartAdapter);
                        cart_total.setText("₹" + String.valueOf(total));
                    }
                }
                else {
                    emptyCart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
