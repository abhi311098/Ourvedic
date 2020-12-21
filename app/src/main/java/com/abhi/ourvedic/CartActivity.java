package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abhi.ourvedic.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    TextView place_order_tv;
    ArrayList<item> item_cart_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        place_order_tv = findViewById(R.id.place_order_tv);
        place_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, Billing_Details.class);
                i.putExtra("cart_item", item_cart_copy);
                startActivity(i);
            }
        });

        item_cart_copy = new ArrayList<>();
        item_cart_copy.add(new item(101, "Agarbatti", "Incense stick",R.drawable.h101));
        item_cart_copy.add(new item(102, "Ghee", "Ghee",R.drawable.h102));
        item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma",R.drawable.h103));
        item_cart_copy.add(new item(104, "phool", "Flowers",R.drawable.h104));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, item_cart_copy);
        ListView cart_item = findViewById(R.id.cart_item);
        cart_item.setAdapter(cartAdapter);
    }
}