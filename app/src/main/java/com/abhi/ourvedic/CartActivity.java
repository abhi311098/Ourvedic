package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class CartActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cart").child(user.getUid()).push();
    Button place_order_tv;
    LinkedHashSet<item> item_cart_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        place_order_tv = findViewById(R.id.place_order_tv);

        item_cart_copy = new LinkedHashSet<>();
        item_cart_copy.add(new item(101, "Agarbatti", "Incense stick", R.drawable.h101, 100));
        item_cart_copy.add(new item(102, "Ghee", "Ghee", R.drawable.h102, 100));
        item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma", R.drawable.h103, 100));
        item_cart_copy.add(new item(104, "phool", "Flowers", R.drawable.h104, 100));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, new ArrayList(item_cart_copy));
        ListView cart_item = findViewById(R.id.cart_item);
        cart_item.setAdapter(cartAdapter);

        place_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Object, Object> hashMap = new HashMap<>();
                if(!item_cart_copy.isEmpty()) {
                    ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if (networkInfo != null) {
                        Iterator<item> iterator = item_cart_copy.iterator();
                        while (iterator.hasNext()) {
                            final item itemdetails = iterator.next();
                            hashMap.put("itemid",itemdetails.getItem_id());
                            hashMap.put("localname",itemdetails.getItem_local_name());
                            hashMap.put("itemname",itemdetails.getItem_name());
                            myRef.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("errorres", "onSuccess: "+itemdetails.getItem_id());
                                    Toast.makeText(CartActivity.this, "done", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CartActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                    } else {
                        Toast.makeText(CartActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                //                Intent i = new Intent(CartActivity.this, Billing_Details.class);
//                i.putExtra("cart_item", item_cart_copy);
//                startActivity(i);
            }
        });


    }

}
