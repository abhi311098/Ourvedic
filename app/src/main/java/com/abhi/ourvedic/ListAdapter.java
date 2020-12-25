package com.abhi.ourvedic;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abhi.ourvedic.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ListAdapter extends ArrayAdapter<com.abhi.ourvedic.item> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("cart").child(user.getUid());
    ArrayList<item> item_cart = new ArrayList();
    HashMap<Object, Object> hashMap = new HashMap<>();
    Vibrator Vibrator;

    public ListAdapter(Activity activity){
        super(activity, 0);
    }

    public ListAdapter(Activity context, ArrayList<com.abhi.ourvedic.item> al) {
        super(context, 0, al);
        Vibrator = (Vibrator)getContext().getSystemService(MainActivity.VIBRATOR_SERVICE);
    }
    public ArrayList get_item_cart(){
        return item_cart;
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
                        item_cart.add(currentitem);
                        Iterator<item> iterator = item_cart.iterator();
                        while (iterator.hasNext()) {
                            final item itemdetails = iterator.next();
                            hashMap.put("itemid",itemdetails.getItem_id());
                            hashMap.put("localname",itemdetails.getItem_local_name());
                            hashMap.put("itemname",itemdetails.getItem_name());
                            hashMap.put("itemprice",itemdetails.getItem_Price());
                            hashMap.put("image",itemdetails.getItem_image());
                            Log.e("errorres", "onClick: "+hashMap );
                            myRef.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("errorres", "onSuccess: "+itemdetails.getItem_id());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        }
                        item_cart.clear();
                        Toast.makeText(getContext(),"Item added!",Toast.LENGTH_SHORT).show();
                        Vibrator.vibrate(500);

                    }
                else {
                        Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

}
        });

        return listItemView;
    }
}
