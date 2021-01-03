package com.abhi.ourvedic.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.abhi.ourvedic.BookDharamshala;
import com.abhi.ourvedic.R;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_booking, container, false);

        TextView tv_Dharamshala = root.findViewById(R.id.tv_Dharamshala);
        tv_Dharamshala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BookDharamshala.class));
            }
        });

        return root;
    }
}