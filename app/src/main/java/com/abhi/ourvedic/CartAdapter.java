package com.abhi.ourvedic;

import android.app.Activity;
import android.os.Vibrator;
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

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<item> implements AdapterView.OnItemSelectedListener {

    String[] quantity = { "1", "2", "3", "4", "5", "6" };
    int quant;
    String amount;
    TextView price;


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

        TextView remove = listItemView.findViewById(R.id.Remove);                                    //for abhishek

        TextView Move_to_WishList = listItemView.findViewById(R.id.Move_to_WishList);                //for abhishek


        Spinner spino = listItemView.findViewById(R.id.spinner);

        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, quantity);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quant = adapterView.getSelectedItemPosition() + 1;
                amount = "₹" + String.valueOf(currentitem.getItem_Price()*quant);
                //price.setText(amount);                                                                     **for abhishek**
                Log.v("text",amount);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
