package com.abhi.ourvedic;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AP_ItemSettingAdapter extends ArrayAdapter<item> {

    Vibrator Vibrator;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("Admin").child("all_items");



    public AP_ItemSettingAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public AP_ItemSettingAdapter(Activity context, ArrayList<item> al) {
        super(context, 0, al);
        Vibrator = (Vibrator)getContext().getSystemService(MainActivity.VIBRATOR_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_setting_changer, parent, false);
        }
        final com.abhi.ourvedic.item currentitem = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.local_name_item__textView);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.default_textView);
        numberTextView.setText(currentitem.getItem_name());

        final EditText priceTextView = listItemView.findViewById(R.id.item_price);
        priceTextView.setText(String.valueOf(currentitem.getItem_Price()));

        ImageView imageResource = listItemView.findViewById(R.id.image);
        imageResource.setImageResource(currentitem.getItem_image());

        ImageView add = listItemView.findViewById(R.id.add); //will be use to change item price
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {

                    int s = Integer.parseInt(String.valueOf(priceTextView.getText()));
                    int i = currentitem.getItem_id()-101;
                    all_itemRef.child(String.valueOf(i)).child("item_Price").setValue(s);

                    Toast.makeText(getContext(),"Price Successfully Updated!",Toast.LENGTH_SHORT).show();
                    Vibrator.vibrate(500);
                }
                else {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView delete_item = listItemView.findViewById(R.id.delete_item);
        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all_itemRef.child(String.valueOf(currentitem.getItem_id()-101)).removeValue();
                Toast.makeText(getContext(), "Item Successfully Removed", Toast.LENGTH_SHORT).show();
                Intent intent = ((Activity)view.getContext()).getIntent();
                ((Activity)view.getContext()).finish();
                ((Activity)view.getContext()).startActivity(intent);;
            }
        });

        return listItemView;
    }
}
