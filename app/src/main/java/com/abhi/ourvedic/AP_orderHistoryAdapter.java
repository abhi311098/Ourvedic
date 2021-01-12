package com.abhi.ourvedic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class AP_orderHistoryAdapter extends ArrayAdapter<order_details> {

    public AP_orderHistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public AP_orderHistoryAdapter(AP_orderHistory ap_orderHistory, ArrayList<order_details> orderHistoryAlist) {
        super(ap_orderHistory, 0, orderHistoryAlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.current_item, parent, false);
        }
        final com.abhi.ourvedic.order_details currentOrder = getItem(position);

        RelativeLayout if_delivered = listItemView.findViewById(R.id.if_delivered);
        if_delivered.setVisibility(View.GONE);

        TextView bName = listItemView.findViewById(R.id.tv_bName);
        bName.setText(currentOrder.getName());

        TextView bEmail = listItemView.findViewById(R.id.bEmail);
        bEmail.setText(currentOrder.getEmail());

        TextView mob = listItemView.findViewById(R.id.mob);
        mob.setText(currentOrder.getMobile());

        final TextView order_date = listItemView.findViewById(R.id.order_date);
        order_date.setText(currentOrder.getOrder_date_time());

        TextView delivey_date = listItemView.findViewById(R.id.delivey_date);
        delivey_date.setText(currentOrder.getDelivered_date_time());

        TextView itemIds = listItemView.findViewById(R.id.itemIds);
        itemIds.setText(currentOrder.getItemIds());

        TextView billing_total = listItemView.findViewById(R.id.billing_total);
        billing_total.setText(String.valueOf(currentOrder.getFinal_amount()));

        TextView modeOfPayment = listItemView.findViewById(R.id.mop);
        modeOfPayment.setText(currentOrder.getMode_of_payment());

        TextView delivery_address = listItemView.findViewById(R.id.delivery_address);
        delivery_address.setText(currentOrder.getDelivery_address());


        return listItemView;
    }

}
