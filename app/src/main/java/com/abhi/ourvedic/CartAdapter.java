package com.abhi.ourvedic;

import android.app.Activity;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

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

    ArrayList<item> map;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");

    CardView cardView;

    String[] quantity = { "1", "2", "3", "4", "5" };
    int quant;
    String amount;
    TextView price;
    TextView removeItem;
    TextView Move_to_WishList;

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
        Log.e("errorres", "abhishek: "+id );
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

        Move_to_WishList = listItemView.findViewById(R.id.Move_to_WishList);                //for abhishek

        Spinner spino = listItemView.findViewById(R.id.spinner);

        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, quantity);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quant = Integer.valueOf(String.valueOf(adapterView.getItemAtPosition(i)));
                amount = String.valueOf(currentitem.getItem_Price()*quant);
                Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
                myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().child("item_Price").setValue(Integer.parseInt(amount));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                price.setText(amount);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return listItemView;
    }

    private void abhishek(int item_id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
