package com.example.carinventory.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carinventory.R;
import com.example.carinventory.adapters.ViewPagerAdapter;
import com.example.carinventory.databinding.FragmentHomeBinding;

public class Home extends Fragment {

    FragmentHomeBinding binding;
    ViewPagerAdapter adapter;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new AllCarsF(), "All Cars"); // Replace with your actual fragments
        adapter.addFragment(new AvailableCarsF(), "Available Cars");
        adapter.addFragment(new SoldCarsF(), "Sold Cars");
        // Add more fragments as needed

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}