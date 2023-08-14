package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityEditCarBinding;
import com.example.carinventory.models.CarModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.paperdb.Paper;

public class EditCar extends AppCompatActivity {

    ActivityEditCarBinding binding;
    String json ;
    static int position;
    ArrayList<CarModel> arrayList = new ArrayList<>();
    CarModel model;
    String  TAG="" , sold_date="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        binding = ActivityEditCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        json = getIntent().getStringExtra("car_details");
        position = getIntent().getIntExtra("position",-1);

        if (json != null && !json.equals("")){
            model = new Gson().fromJson(json, CarModel.class);
            getData();
        }
        binding.btnUpdate.setOnClickListener(v -> {
            validation();
        });
        binding.icBack.setOnClickListener(v -> {
            finish();
        });

    }
    private void getData(){
        if (!model.make.equals("")){
            binding.etCompany.setText(model.make);
        }
        if (!model.model.equals("")){
            binding.etModel.setText(model.model);
        }
        if (!model.condition.equals("")){
            binding.etCondition.setText(model.condition);
        }
        if (!model.engine_cylinder.equals("")){
            binding.etEngineCylinder.setText(model.engine_cylinder);
        }
        if (!model.year.equals("")){
            binding.etYear.setText(model.year);
        }
        if (!model.no_of_doors.equals("")){
            binding.etNoOffDoors.setText(model.no_of_doors);
        }
        if (!model.price.equals("")){
            binding.etPrice.setText(model.price);
        }
        if (!model.color.equals("")){
            binding.etColor.setText(model.color);
        }
        if (!model.image.equals("")){
            Bitmap bm = BitmapFactory.decodeFile(model.image);
            binding.imgCar.setImageBitmap(bm);
        }
    }
    private void validation(){
        if (binding.etCompany.getText().toString().equals("")){
            binding.etCompany.setError("Enter the company name !!!");
        }else if (binding.etModel.getText().toString().equals("")){
            binding.etModel.setError("Enter the model name !!!");
        }else if (binding.etCondition.getText().toString().equals("")){
            binding.etCondition.setError("Enter the condition !!!");
        }else if (binding.etEngineCylinder.getText().toString().equals("")){
            binding.etEngineCylinder.setError("Enter the engine cylinder !!!");
        }else if (binding.etYear.getText().toString().equals("")){
            binding.etYear.setError("Enter the year !!!");
        }else if (binding.etNoOffDoors.getText().toString().equals("")){
            binding.etNoOffDoors.setError("Enter the no of doors !!!");
        }else if (binding.etPrice.getText().toString().equals("")){
            binding.etPrice.setError("Enter the price !!!");
        }else if (binding.etColor.getText().toString().equals("")){
            binding.etColor.setError("Enter the color !!!");
        } else {
            addData();
        }


    }
    private void addData(){
        arrayList.clear();
        try {
            ArrayList<CarModel> carModels = Paper.book().read("carsArrayList", new ArrayList<CarModel>());
            if (carModels != null) {
                arrayList.addAll(carModels);
            } else {
                // Handle the case where no data is found in PaperDB
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        arrayList.remove(arrayList.get(position));
        arrayList.add(new CarModel(binding.etCompany.getText().toString(),
                binding.etModel.getText().toString(),
                binding.etCondition.getText().toString(),
                binding.etEngineCylinder.getText().toString(),
                binding.etYear.getText().toString(),
                binding.etNoOffDoors.getText().toString(),
                binding.etPrice.getText().toString(),
                binding.etColor.getText().toString(),
                model.image,
                model.is_sold,
                sold_date
        ));
        Toast.makeText(this, "Data updated successfully !!!", Toast.LENGTH_SHORT).show();
        try {
            Paper.book().write("carsArrayList", arrayList);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "addData: " + String.valueOf(arrayList.size()));
        finish();


    }
}