package com.example.carinventory.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carinventory.activities.CompanyDetailsA;
import com.example.carinventory.activities.EditCompanyA;
import com.example.carinventory.adapters.CompanyAdapter;
import com.example.carinventory.databinding.FragmentStatsBinding;
import com.example.carinventory.models.CompanyModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.paperdb.Paper;

public class Stats extends Fragment {

    FragmentStatsBinding binding;
    ArrayList<CompanyModel> arrayList = new ArrayList<>();
    CompanyAdapter adapter;
    public Stats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater,container,false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new CompanyAdapter(getActivity(), arrayList, (position, type) -> {
            CompanyModel model =arrayList.get(position);
            String json = new Gson().toJson(model);

            if (type == 1){
                Intent intent = new Intent(getActivity(), CompanyDetailsA.class);
                intent.putExtra("company_details",json);
                intent.putExtra("position",position);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getActivity(), EditCompanyA.class);
                intent.putExtra("company_details",json);
                intent.putExtra("position",position);
                startActivity(intent);
            }

        });
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }
    private void refreshData(){
        arrayList.clear();
        try {
            ArrayList<CompanyModel> models = Paper.book().read("companyArrayList", new ArrayList<CompanyModel>());
            if (models != null) {
                arrayList.addAll(models);
                adapter.notifyDataSetChanged();
            } else {
                // Handle the case where no data is found in PaperDB
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}