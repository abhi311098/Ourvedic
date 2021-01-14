package com.abhi.ourvedic;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

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

public class CartAdapter extends ArrayAdapter<item> implements AdapterView.OnItemSelectedListener {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_wishlist");

    CardView cardView;

    public CartAdapter(Activity context, ArrayList<item> cal) {
        super(context, 0, cal);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_list_item, parent, false);
        }
        final com.abhi.ourvedic.item currentitem = getItem(position);

        final TextView price;
        TextView removeItem;
        TextView move_to_wishList;

        TextView nameTextView = listItemView.findViewById(R.id.local_name_item__textView);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.default_textView);
        numberTextView.setText(currentitem.getItem_name());

        ImageView imageResource = listItemView.findViewById(R.id.image_view);
        imageResource.setImageResource(currentitem.getItem_image());

        price = listItemView.findViewById(R.id.item_price);
        Log.v("price",String.valueOf(currentitem.getItem_Price()));
        price.setText(String.valueOf(currentitem.getItem_Price()));

        removeItem = listItemView.findViewById(R.id.Remove);
        final int id = currentitem.getItem_id();

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Query myCartRefquery = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(id);
                myCartRefquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        cardView = listItemView.findViewById(R.id.cardview);

        move_to_wishList = listItemView.findViewById(R.id.move_to_cart);
        move_to_wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                wishlistRef.push().setValue(currentitem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("item ", "added to wishlist");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("item ", "added to wishlist failed");
                    }
                });

                Query myCartRefquery = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(id);
                myCartRefquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Moved to Wishlist", Toast.LENGTH_SHORT).show();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /*final Spinner spino = listItemView.findViewById(R.id.spinner);

        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, quantity);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quant = Integer.parseInt((String) adapterView.getSelectedItem());


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        final int[] quant = {currentitem.getItem_quant()};
        final TextView qunatity = listItemView.findViewById(R.id.quantity);
        qunatity.setText(String.valueOf(quant[0]));

        ImageView add_cart = listItemView.findViewById(R.id.add_cart);
        ImageView reduce = listItemView.findViewById(R.id.remove_cart);

        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] amount = {0};
                if (quant[0]<7){
                    qunatity.setText(String.valueOf(++quant[0]));
                    Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                database.getReference("Admin").child("all_items").orderByChild("item_id").equalTo(currentitem.getItem_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            for(DataSnapshot dss : snapshot.getChildren()){
                                                item i = dss.getValue(item.class);
                                                Log.v("Tag", String.valueOf(i.getItem_Price()));
                                                amount[0] = i.getItem_Price()*quant[0];
                                                dataSnapshot.getRef().child("item_Price").setValue(amount[0]);
                                                dataSnapshot.getRef().child("item_quant").setValue(quant[0]);
                                                price.setText(String.valueOf(amount[0]));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] amount = {0};
                if(quant[0] >1){
                    qunatity.setText(String.valueOf(--quant[0]));
                    Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                database.getReference("Admin").child("all_items").orderByChild("item_id").equalTo(currentitem.getItem_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            for(DataSnapshot dss : snapshot.getChildren()){
                                                item i = dss.getValue(item.class);
                                                amount[0] = i.getItem_Price()*quant[0];
                                                dataSnapshot.getRef().child("item_Price").setValue(amount[0]);
                                                dataSnapshot.getRef().child("item_quant").setValue(quant[0]);
                                                price.setText(String.valueOf(amount[0]));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return listItemView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
