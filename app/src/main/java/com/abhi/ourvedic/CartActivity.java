package com.abhi.ourvedic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.abhi.ourvedic.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ArrayList<item> item_cart_copy = new ArrayList<>();
        item_cart_copy.add(new item(101, "Agarbatti", "Incense stick",R.drawable.h101));
        item_cart_copy.add(new item(102, "Ghee", "Ghee",R.drawable.h102));
        item_cart_copy.add(new item(103, "Kumkuma", "Kumkuma",R.drawable.h103));
        item_cart_copy.add(new item(104, "phool", "Flowers",R.drawable.h104));

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, item_cart_copy);
        ListView cart_item = findViewById(R.id.cart_item);
        cart_item.setAdapter(cartAdapter);
    }
}