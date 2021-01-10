package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Billing_Details extends AppCompatActivity {

    private String TAG = "errorres";
    Button confirmorder;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<item> item_cart_copy2;
    int total = 0;
    TextView billing_name;
    TextView billing_number;
    TextView billing_price;
    TextView billing_delivery_charges;
    TextView billing_amount_final;
    TextView billing_address;

    String h_no,area,pincode,street,land;

    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference myProfileRef = database.getReference("Profile").child(user.getUid());
    DatabaseReference myHistoryRef = database.getReference("users").child(user.getUid()).child("user_orderHistory");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing__details);

        confirmorder = findViewById(R.id.confirmqqorder);
        billing_price = findViewById(R.id.billing_price);
        billing_address = findViewById(R.id.billing_address);
        billing_name = findViewById(R.id.billing_name);
        billing_number = findViewById(R.id.billing_number);
        billing_delivery_charges = findViewById(R.id.billing_delivery_charges);
        billing_amount_final = findViewById(R.id.billing_amount_final);
        item_cart_copy2 = new ArrayList<>();

        myProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    h_no = snapshot.child("house").getValue(String.class);
                    street = snapshot.child("street").getValue(String.class);
                    area = snapshot.child("area").getValue(String.class);
                    land = snapshot.child("land").getValue(String.class);
                    pincode = snapshot.child("pincode").getValue(String.class);
                    billing_name.setText(snapshot.child("name").getValue(String.class));
                    billing_number.setText(snapshot.child("number").getValue(String.class));
                    billing_address.setText(h_no+" "+area+" "+street+" "+land+" "+pincode);
                } else {
                    billing_address.setText("Go To Profile Section And Complete Your Profile First");
                    Toast.makeText(Billing_Details.this, "No Address Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Billing_Details.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        myCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dss : snapshot.getChildren()){
                        item i = dss.getValue(item.class);
                        item_cart_copy2.add(i);
                        total += i.getItem_Price();
                        billing_price.setText("₹" + String.valueOf(total));
                        billing_amount_final.setText("₹" + String.valueOf(total));
                        BillingAdapter billingAdapter = new BillingAdapter(Billing_Details.this, item_cart_copy2);
                        ListView billing_lv = findViewById(R.id.billing_lv);
                        billing_lv.setAdapter(billingAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Billing_Details.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {

                    item o = new item(currentitem.getItem_id(),currentitem.getItem_local_name(),currentitem.getItem_name(), currentitem.getItem_image(), currentitem.getItem_Price());

                    objectList.add(o);

                    myHistoryRef.setValue(objectList).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "onSuccess: done" );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: "+e.getMessage());
                        }
                    });
                    Toast.makeText(getContext(),"Item added!",Toast.LENGTH_SHORT).show();
                    Vibrator.vibrate(500);
                }
                else {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }*/

                //Task<Void> myHistoryRef = database.getReference("users").child(user.getUid()).child("user_cart").removeValue();
                startActivity(new Intent(Billing_Details.this,Splash_Screen2.class));
                finish();
            }
        });
    }

    /*void makeOrderHistory(){
        myHistoryRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: Done");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure : failed");
            }
        });
    }*/

}
