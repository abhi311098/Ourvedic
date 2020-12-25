package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Billing_Details extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Profile").child(user.getUid());
    DatabaseReference myCartRef = database.getReference("cart").child(user.getUid());
    DatabaseReference myHistoryRef = database.getReference("History").child(user.getUid());
    String area, house, land, pincode, street;
    private String TAG = "errorres";
    Object itemname1, localname1, itemid1, itemprice;
    Button confirmorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing__details);

        confirmorder = findViewById(R.id.confirmqqorder);

        confirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Billing_Details.this,MainActivity.class));
            }
        });

        addressDetails();

        cartDetails();

        confirmOrderDetails();
    }

    private void confirmOrderDetails() {


    }

    private void cartDetails() {

    }

    private void addressDetails() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<Object, String> map = (Map) snapshot.getValue();
                if (map != null) {
                    house = map.get("house");
                    street = map.get("street");
                    house = map.get("house");
                    land = map.get("land");
                    pincode = map.get("pincode");
                }
                Log.e(TAG, "onDataChange: " + house + "\n" + street + "\n" + area + "\n" + land + "\n" + pincode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myCartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Map<Object, Object> map = (Map) postSnapshot.getValue();
                        if (map != null) {
                            itemname1 = map.get("itemname");
                            localname1 = map.get("localname");
                            itemid1 = map.get("itemid");
                            itemprice = map.get("itemprice");
                            Log.e(TAG, "onDataChange: " + itemid1 + "\n" + itemname1 + "\n"
                                    + localname1 + "\n" + itemprice);
//
//                            HashMap<Object, Object> hashMap = new HashMap<>();
//                            hashMap.put("itemid", itemid1);
//                            hashMap.put("itemname", itemname1);
//                            hashMap.put("localname", localname1);
//                            hashMap.put("itemprice", itemprice);
//                            myHistoryRef.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.e(TAG, "onSuccess: Done");
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//
//                                }
//                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Billing_Details.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
