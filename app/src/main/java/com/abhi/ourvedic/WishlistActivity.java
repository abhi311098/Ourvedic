package com.abhi.ourvedic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_wishlist");

    ListView lv_wishlist;
    ArrayList<item> wishlistAlist;
    RelativeLayout emptyWishlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        lv_wishlist = findViewById(R.id.lv_wishlist);
        wishlistAlist = new ArrayList<>();
        emptyWishlist = findViewById(R.id.emptyWishlist);

        wishlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    emptyWishlist.setVisibility(View.GONE);
                    for(DataSnapshot dss : snapshot.getChildren()){
                        wishlistAlist.add(dss.getValue(item.class));
                        ListAdapter wishlistAdapter = new ListAdapter(WishlistActivity.this, wishlistAlist, true);
                        lv_wishlist.setAdapter(wishlistAdapter);
                    }
                }
                else {
                    emptyWishlist.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}