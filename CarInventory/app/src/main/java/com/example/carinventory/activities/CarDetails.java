package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityCarDetailsBinding;
import com.example.carinventory.models.CarModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CarDetails extends AppCompatActivity {

    ActivityCarDetailsBinding binding;
    ArrayList<CarModel> arrayList = new ArrayList<>();
    String json;
    int position;
    CarModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        binding = ActivityCarDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        json = getIntent().getStringExtra("car_details");
        position = getIntent().getIntExtra("position",-1);

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
        if (!json.equals("")) {
            model = new Gson().fromJson(json, CarModel.class);
            getData();

        }
        binding.icBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnPurchase.setOnClickListener(v -> {
            if (model.is_sold){
                Toast.makeText(this, "Car is sold out !!!", Toast.LENGTH_SHORT).show();
            }else {
                model.is_sold = true;
                binding.txtIsSold.setText("Sold Out");
                binding.txtIsSold.setTextColor(getColor(R.color.my_color));
                arrayList.remove(arrayList.get(position));
                arrayList.add(model);
                try {
                    Paper.book().write("carsArrayList",arrayList);
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "Car purchased successfully !!!", Toast.LENGTH_SHORT).show();
            }

        });

    }
    private void getData(){
        if (!model.make.equals("")){
            binding.txtCompany.setText(model.make);
        }
        if (!model.model.equals("")){
            binding.txtModel.setText(model.model);
            binding.textModel.setText(model.model);
        }
        if (!model.year.equals("")){
            binding.txtYear.setText(model.year);
        }
        if (!model.price.equals("")){
            binding.txtPrice.setText(model.price);
        }
        if (!model.condition.equals("")){
            binding.txtCondition.setText(model.condition);
        }
        if (!model.engine_cylinder.equals("")){
            binding.txtEngineCylinder.setText(model.engine_cylinder);
        }
        if (!model.no_of_doors.equals("")){
            binding.txtNoOfDoors.setText(model.no_of_doors);
        }
        if (!model.color.equals("")){
            binding.txtColor.setText(model.color);
        }

        if (model.is_sold){
            binding.txtIsSold.setText("Sold Out");
            binding.txtIsSold.setTextColor(getColor(R.color.my_color));
        }


        if (!model.image.equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(model.image);
            binding.imgCar.setImageBitmap(bitmap);
        }else {
            binding.imgCar.setImageResource(R.drawable.car);
        }
    }
}