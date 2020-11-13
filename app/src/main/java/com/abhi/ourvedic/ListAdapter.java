package com.abhi.ourvedic;

import android.app.Activity;
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

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<com.abhi.ourvedic.item> {

    ArrayList <Integer> item_cart = new ArrayList<>();

    public ListAdapter(Activity context, ArrayList<com.abhi.ourvedic.item> al) {
        super(context, 0, al);
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

        ImageView imageResource = listItemView.findViewById(R.id.image);
        imageResource.setImageResource(currentitem.getItem_image());

        TextView add = listItemView.findViewById(R.id.item_add); //will be use to add item in cart
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_cart.add(currentitem.getItem_id());
                Toast.makeText(getContext(),"Item added!",Toast.LENGTH_SHORT).show();
            }
        });




        return listItemView;
    }
}
