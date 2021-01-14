package com.abhi.ourvedic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.abhi.ourvedic.ui.OrderHistory.OrderHistoryFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryAdapter extends ArrayAdapter<order_details> {

    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("Admin").child("all_items");

    public OrderHistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public OrderHistoryAdapter(FragmentActivity activity, ArrayList<order_details> orderHistoryAlist) {
        super(activity, 0, orderHistoryAlist);
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

        TextView tv_itemnames = listItemView.findViewById(R.id.tv_itemnames);
        tv_itemnames.setVisibility(View.VISIBLE);
        final TextView tv_itemIds = listItemView.findViewById(R.id.tv_itemIds);
        tv_itemIds.setVisibility(View.INVISIBLE);
        final TextView itemIds = listItemView.findViewById(R.id.itemIds);
        List<String> namesAlist = Arrays.asList(currentOrder.getItemIds().split("-"));
        final ArrayList<String> names = new ArrayList<>();
        for(final String s : namesAlist){
            Log.v("id", s);
            Query q = all_itemRef.orderByChild("item_id").equalTo(Integer.parseInt(s.substring(0,3)));
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss: snapshot.getChildren()){
                            item d = dss.getValue(item.class);
                            names.add(d.getItem_local_name() + s.substring(3,s.length()));
                            Log.v("id", String.valueOf(names.get(0)==null));
                            itemIds.setText(names.toString());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        TextView billing_total = listItemView.findViewById(R.id.billing_total);
        billing_total.setText(String.valueOf(currentOrder.getFinal_amount()));

        TextView modeOfPayment = listItemView.findViewById(R.id.mop);
        modeOfPayment.setText(currentOrder.getMode_of_payment());

        TextView delivery_address = listItemView.findViewById(R.id.delivery_address);
        delivery_address.setText(currentOrder.getDelivery_address());

        return listItemView;
    }
}
