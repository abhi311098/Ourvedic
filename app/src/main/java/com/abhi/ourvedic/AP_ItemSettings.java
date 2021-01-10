package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AP_ItemSettings extends AppCompatActivity {

    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("Admin").child("all_items");

    ArrayList<item> itemArrayList;
    RelativeLayout add_new_item;
    Dialog add_new_item_dialog;
    int price=0;
    ListView lv_current_items;
    itemSettingAdapter itemSettingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_settings);

        add_new_item = findViewById(R.id.add_new_item);
        lv_current_items = findViewById(R.id.lv_current_items);


        ConnectivityManager manager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            all_itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss : snapshot.getChildren()){
                            item i = dss.getValue(item.class);
                            itemArrayList.add(i);
                            itemSettingAdapter = new itemSettingAdapter(AP_ItemSettings.this, itemArrayList);
                            lv_current_items.setAdapter(itemSettingAdapter);
                        }
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

        itemArrayList = new ArrayList<>();
        /*itemArrayList.add(new item(101, "Agarbatti", "Incense stick",R.drawable.h101, 100));
        itemArrayList.add(new item(102, "Ghee", "Ghee",R.drawable.h102, 100));
        itemArrayList.add(new item(103, "Kumkuma", "Kumkuma",R.drawable.h103, 100));
        itemArrayList.add(new item(104, "Phool", "Flowers",R.drawable.h104, 100));
        itemArrayList.add(new item(105, "Rudraksha", "Rudraksha",R.drawable.h105, 100));
        itemArrayList.add(new item(106, "Chandan", "Sandalwood",R.drawable.h106, 100));
        itemArrayList.add(new item(107, "Sindoor", "Vermilion red",R.drawable.h107, 100));
        itemArrayList.add(new item(108, "Tulasi", "Tulasi",R.drawable.h108, 100));
        itemArrayList.add(new item(109, "Haldee", "Turmeric",R.drawable.h109, 100));
        itemArrayList.add(new item(110, "Vibhuti", "Vibhuti",R.drawable.h110, 100));
        itemArrayList.add(new item(111, "Panchagavya", "Panchagavya ",R.drawable.h111, 100));
        itemArrayList.add(new item(112, "Dhaga", "Red Thread",R.drawable.h112, 100));
        itemArrayList.add(new item(113, "Cheenee", "Sugar",R.drawable.h113, 100));
        itemArrayList.add(new item(114, "Prasad", "Prasad",R.drawable.h114, 100));
        itemArrayList.add(new item(115, "Havan Samagri", "Havan content",R.drawable.h115, 100));
        itemArrayList.add(new item(116, "Diya Stand", "Light lamps",R.drawable.h116, 100));
        itemArrayList.add(new item(117, "Lakadee", "Firewood",R.drawable.h117, 100));
        itemArrayList.add(new item(118, "Doodh", "Milk",R.drawable.h118, 100));
        itemArrayList.add(new item(119, "Shahad", "Honey",R.drawable.h119, 100));
        itemArrayList.add(new item(120, "Kesar", "Saffron",R.drawable.h120, 100));
        itemArrayList.add(new item(121, "Kheer", "Kheer",R.drawable.h121, 100));
        itemArrayList.add(new item(122, "Panjiri", "Panjiri",R.drawable.h122, 100));
        itemArrayList.add(new item(123, "Naariyal", "Coconut",R.drawable.h123, 100));
        itemArrayList.add(new item(124, "Saphed Dhaaga", "White thread",R.drawable.h124, 100));
        itemArrayList.add(new item(125, "Phal", "Fruits",R.drawable.h125, 100));
        itemArrayList.add(new item(126, "Chawal", "Rice",R.drawable.h126, 100));
        itemArrayList.add(new item(127, "Elaichi", "Cardamom ",R.drawable.h127, 100));
        itemArrayList.add(new item(128, "Chandan", "Sandalwood",R.drawable.h128, 100));
        itemArrayList.add(new item(129, "Halva", "Halva",R.drawable.h129, 100));
        itemArrayList.add(new item(130, "Pila Chawal", "Yellow rice",R.drawable.h130, 100));
        itemArrayList.add(new item(131, "Peele Vastr", "Yellow clothes",R.drawable.h131, 100));
        itemArrayList.add(new item(132, "Agarwood", "Agarwood",R.drawable.h132, 100));
        itemArrayList.add(new item(133, "Laung", "Cloves",R.drawable.h133, 100));*/


        add_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_new_item_dialog = new Dialog(AP_ItemSettings.this);
                add_new_item_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                add_new_item_dialog.setContentView(R.layout.add_new_item_dailog);
                add_new_item_dialog.show();
                final EditText et_localname = add_new_item_dialog.findViewById(R.id.et_localname);
                final EditText et_default_name = add_new_item_dialog.findViewById(R.id.et_default_name);
                final EditText et_price = add_new_item_dialog.findViewById(R.id.et_price);
                Button add = add_new_item_dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(et_localname.getText()!=null && et_default_name!=null && et_price!=null){
                            String localname = String.valueOf(et_localname.getText());
                            String default_name = String.valueOf(et_default_name.getText());
                            price = Integer.parseInt(String.valueOf(et_price.getText()));
                            item newItem = new item(itemArrayList.size()+101, localname, default_name, R.drawable.null_img, price);
                            itemArrayList.add(newItem);
                            all_itemRef.setValue(itemArrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.v("onSuccess","done!");
                                    itemSettingAdapter.notifyDataSetChanged();
                                    add_new_item_dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v("onFailure", "Failed!");
                                }
                            });
                        }
                        else {
                            Toast.makeText(AP_ItemSettings.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}