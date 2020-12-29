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
    DatabaseReference myCartRef = database.getReference("cart").child(user.getUid());
    DatabaseReference myHistoryRef = database.getReference("History").child(user.getUid());
    Button place_order_tv;
    String itemname1, localname1;
    long itemid1, itemprice;
    ArrayList<item> item_cart_copy;
    private String TAG = "errorres";
    int item_image;

    Map<Object, Object> map;
    //HashMap<Object, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        place_order_tv = findViewById(R.id.place_order_tv);


        item_cart_copy = new ArrayList<>();
//        item_cart_copy.add(new item(101, "Agarbatti", "Incense stick", R.drawable.h101, 100));
//        item_cart_copy.add(new item(102, "Ghee", "Ghee", R.drawable.h102, 100));
//        item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma", R.drawable.h103, 100));
//        item_cart_copy.add(new item(104, "phool", "Flowers", R.drawable.h104, 100));

        startyourjalwa();

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, item_cart_copy);
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

    private void startyourjalwa() {
        myCartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        map = (Map<Object, Object>) postSnapshot.getValue();
                        if (map != null) {
                            itemname1 = (String) map.get("itemname");
                            localname1 = (String) map.get("localname");
                            itemid1 = (long) map.get("itemid");
                            itemprice = (long) map.get("itemprice");
                            item_cart_copy.add(new item ((int) itemid1, localname1, itemname1, R.drawable.h133, (int) itemprice));
                            /*hashMap = new HashMap<>();
                            hashMap.put("itemid", itemid1);
                            hashMap.put("itemname", itemname1);
                            hashMap.put("localname", localname1);
                            hashMap.put("itemprice", itemprice);*/
                            myHistoryRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e(TAG, "onSuccess: Done");
                                    Log.v("items", String.valueOf(item_cart_copy.get(item_cart_copy.size()-1).getItem_local_name()));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
