package com.abhi.ourvedic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abhi.ourvedic.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ListAdapter extends ArrayAdapter<com.abhi.ourvedic.item> {

    String TAG = "errorres";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference myWistlistRef = database.getReference("users").child(user.getUid()).child("user_wishlist");
    List<Object> objectList = new ArrayList<>();
    Vibrator Vibrator;
    boolean callByWishlist;

    public ListAdapter(Activity activity){
        super(activity, 0);
    }

    public ListAdapter(Activity context, ArrayList<com.abhi.ourvedic.item> al, boolean callByWishlist) {
        super(context, 0, al);
        this.callByWishlist = callByWishlist;
        Vibrator = (Vibrator)getContext().getSystemService(MainActivity.VIBRATOR_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }
        final com.abhi.ourvedic.item currentitem = getItem(position);

        LinearLayout if_wishlist = listItemView.findViewById(R.id.if_wishlist);
        if_wishlist.setVisibility(View.GONE);


        TextView nameTextView = listItemView.findViewById(R.id.local_name_item__textView);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.default_textView);
        numberTextView.setText(currentitem.getItem_name());

        TextView priceTextView = listItemView.findViewById(R.id.item_price);
        priceTextView.setText("₹" + String.valueOf(currentitem.getItem_Price()));

        ImageView imageResource = listItemView.findViewById(R.id.image);
        imageResource.setImageResource(currentitem.getItem_image());

        TextView add = listItemView.findViewById(R.id.item_add); //will be use to add item in cart
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {

                item o = new item(currentitem.getItem_id(),currentitem.getItem_local_name(),currentitem.getItem_name(), currentitem.getItem_image(), currentitem.getItem_Price());

                objectList.add(o);

                myRef.setValue(objectList).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
            }
        });

        if(callByWishlist){
            if_wishlist.setVisibility(View.VISIBLE);
            View divider = listItemView.findViewById(R.id.divider);
            divider.setVisibility(View.GONE);
            final ArrayList<item> wishlistAlist_copy = new ArrayList<>();

            TextView move_to_cart = listItemView.findViewById(R.id.move_to_cart);
            move_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Query myWishlistQuery = myWistlistRef.orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myWishlistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            wishlistAlist_copy.add(currentitem);
                            myRef.setValue(wishlistAlist_copy);
                            myWishlistQuery.getRef().removeValue();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            TextView wishlist_remove = listItemView.findViewById(R.id.wishlist_remove);
            wishlist_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Query myWishlistQuery = myWistlistRef.orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myWishlistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myWishlistQuery.getRef().removeValue();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });
        }

        return listItemView;
    }
}