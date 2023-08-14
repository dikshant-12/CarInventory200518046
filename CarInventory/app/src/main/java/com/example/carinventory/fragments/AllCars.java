package com.example.carinventory.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.activities.CarDetailsA;
import com.example.carinventory.activities.EditCarA;
import com.example.carinventory.adapters.CarsAdapter;
import com.example.carinventory.databinding.FragmentAllCarsBinding;
import com.example.carinventory.models.CarModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.paperdb.Paper;


public class AllCars extends Fragment {

    FragmentAllCarsBinding binding;
    CarsAdapter adapter;
    ArrayList<CarModel> arrayList  = new ArrayList<>();
    CarModel carModel  = new CarModel("Ferrari","s90","Good","v8","2022","4","60000","White","",false,"");
    String TAG = "AllCars";
    public AllCars() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllCarsBinding.inflate(inflater,container,false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new CarsAdapter(getActivity(), arrayList, (position, type) -> {
            CarModel model = arrayList.get(position);
            String json = new Gson().toJson(model);
            if(type == 1){
                Intent intent = new Intent(getActivity(), CarDetailsA.class);
                intent.putExtra("car_details",json);
                intent.putExtra("position",position);
                startActivity(intent);

            }else {
                Intent intent = new Intent(getActivity(), EditCarA.class);
                intent.putExtra("car_details",json);
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
            ArrayList<CarModel> carModels = Paper.book().read("carsArrayList", new ArrayList<CarModel>());
            if (carModels != null) {
                arrayList.addAll(carModels);
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