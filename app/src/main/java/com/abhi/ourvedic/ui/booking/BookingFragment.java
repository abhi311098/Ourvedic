package com.abhi.ourvedic.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.abhi.ourvedic.BookDharamshala;
import com.abhi.ourvedic.R;
import com.abhi.ourvedic.item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myCartRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_cart");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_booking, container, false);

        TextView add_panditji = root.findViewById(R.id.add_panditji);
        add_panditji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item i = new item(011, "pandit", "hindu priest", R.drawable.pandit, 0,1);
                myCartRef.push().setValue(i);
                Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        TextView add_Molviji = root.findViewById(R.id.add_Molviji);
        add_Molviji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item i = new item(012, "molvi", "muslim priest", R.drawable.molvi, 0,1);
                myCartRef.push().setValue(i);
                Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        TextView add_father = root.findViewById(R.id.add_father);
        add_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item i = new item(013, "father", "cristian priest", R.drawable.father, 0,1);
                myCartRef.push().setValue(i);
                Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}