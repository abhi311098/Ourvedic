package com.abhi.ourvedic.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.abhi.ourvedic.CartActivity;
import com.abhi.ourvedic.CartAdapter;
import com.abhi.ourvedic.LoginPage;
import com.abhi.ourvedic.MainActivity;
import com.abhi.ourvedic.R;
import com.abhi.ourvedic.ListAdapter;
import com.abhi.ourvedic.Splash_Screen2;
import com.abhi.ourvedic.ViewPagerAdapter;
import com.abhi.ourvedic.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class HomeFragment extends Fragment {

    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("Admin").child("all_items");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        final ArrayList<item> items = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            all_itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss : snapshot.getChildren()){
                            item i = dss.getValue(item.class);
                            items.add(i);
                            progressDialog.dismiss();
                            ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false);
                            ListView listView = root.findViewById(R.id.list);
                            listView.setAdapter(itemsAdapter);
                        }
                    }
                    else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        /*items.add(new item(101, "Agarbatti", "Incense stick",R.drawable.h101, 100));
        items.add(new item(102, "Ghee", "Ghee",R.drawable.h102, 100));
        items.add(new item(103, "Kumkuma", "Kumkuma",R.drawable.h103, 100));
        items.add(new item(104, "Phool", "Flowers",R.drawable.h104, 100));
        items.add(new item(105, "Rudraksha", "Rudraksha",R.drawable.h105, 100));
        items.add(new item(106, "Chandan", "Sandalwood",R.drawable.h106, 100));
        items.add(new item(107, "Sindoor", "Vermilion red",R.drawable.h107, 100));
        items.add(new item(108, "Tulasi", "Tulasi",R.drawable.h108, 100));
        items.add(new item(109, "Haldee", "Turmeric",R.drawable.h109, 100));
        items.add(new item(110, "Vibhuti", "Vibhuti",R.drawable.h110, 100));
        items.add(new item(111, "Panchagavya", "Panchagavya ",R.drawable.h111, 100));
        items.add(new item(112, "Dhaga", "Red Thread",R.drawable.h112, 100));
        items.add(new item(113, "Cheenee", "Sugar",R.drawable.h113, 100));
        items.add(new item(114, "Prasad", "Prasad",R.drawable.h114, 100));
        items.add(new item(115, "Havan Samagri", "Havan content",R.drawable.h115, 100));
        items.add(new item(116, "Diya Stand", "Light lamps",R.drawable.h116, 100));
        items.add(new item(117, "Lakadee", "Firewood",R.drawable.h117, 100));
        items.add(new item(118, "Doodh", "Milk",R.drawable.h118, 100));
        items.add(new item(119, "Shahad", "Honey",R.drawable.h119, 100));
        items.add(new item(120, "Kesar", "Saffron",R.drawable.h120, 100));
        items.add(new item(121, "Kheer", "Kheer",R.drawable.h121, 100));
        items.add(new item(122, "Panjiri", "Panjiri",R.drawable.h122, 100));
        items.add(new item(123, "Naariyal", "Coconut",R.drawable.h123, 100));
        items.add(new item(124, "Saphed Dhaaga", "White thread",R.drawable.h124, 100));
        items.add(new item(125, "Phal", "Fruits",R.drawable.h125, 100));
        items.add(new item(126, "Chawal", "Rice",R.drawable.h126, 100));
        items.add(new item(127, "Elaichi", "Cardamom ",R.drawable.h127, 100));
        items.add(new item(128, "Chandan", "Sandalwood",R.drawable.h128, 100));
        items.add(new item(129, "Halva", "Halva",R.drawable.h129, 100));
        items.add(new item(130, "Pila Chawal", "Yellow rice",R.drawable.h130, 100));
        items.add(new item(131, "Peele Vastr", "Yellow clothes",R.drawable.h131, 100));
        items.add(new item(132, "Agarwood", "Agarwood",R.drawable.h132, 100));
        items.add(new item(133, "Laung", "Cloves",R.drawable.h133, 100));*/



        viewPager = root.findViewById(R.id.viewPager);
        SliderDots = root.findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            SliderDots.addView(dots[i],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new myTimeTask(), 2000,4000);

        return root;
    }

    public class myTimeTask extends TimerTask{
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else if (viewPager.getCurrentItem() == 2) {
                            viewPager.setCurrentItem(3);
                        } else if (viewPager.getCurrentItem() == 3) {
                            viewPager.setCurrentItem(4);
                        } else if (viewPager.getCurrentItem() == 4) {
                            viewPager.setCurrentItem(5);
                        } else if (viewPager.getCurrentItem() == 5) {
                            viewPager.setCurrentItem(6);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}