package com.abhi.ourvedic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AP_CurrentOrderAdapter extends ArrayAdapter<order_details> {

    android.os.Vibrator Vibrator;
    DatabaseReference adminCurrentOrderRef = FirebaseDatabase.getInstance().getReference("Admin").child("current_orders");
    DatabaseReference adminOrderHistoryRef = FirebaseDatabase.getInstance().getReference("Admin").child("order_history");

    public AP_CurrentOrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public AP_CurrentOrderAdapter(AP_Current_Order ap_current_order, ArrayList<order_details> current_order_ap_al) {
        super(ap_current_order,0, current_order_ap_al);
        Vibrator = (Vibrator)getContext().getSystemService(AP_Current_Order.VIBRATOR_SERVICE);
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


        RelativeLayout if_delivered = listItemView.findViewById(R.id.if_delivered); //will be use to change item price
        if_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Query if_delivered_query = adminCurrentOrderRef.orderByChild("order_date_time").equalTo(currentOrder.getOrder_date_time());
                if_delivered_query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DateFormat df = new SimpleDateFormat("KK:mm:ss a, dd/MM/yyyy", Locale.getDefault());
                        String newDeliveryDateTime = df.format(new Date());
                        order_details o = new order_details(currentOrder.getName(), currentOrder.getEmail(), currentOrder.getItemIds(), currentOrder.getDelivery_address(),
                                currentOrder.getMobile(), currentOrder.getFinal_amount(), currentOrder.getMode_of_payment(), currentOrder.getOrder_date_time(), newDeliveryDateTime);
                        adminOrderHistoryRef.push().setValue(o).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.v("admin history","added");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("admin history","failed");
                            }
                        });
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Item is now set to Delivered", Toast.LENGTH_SHORT).show();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return listItemView;
    }
}
