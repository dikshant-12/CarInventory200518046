package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityEditCompanyBinding;
import com.example.carinventory.models.CarModel;
import com.example.carinventory.models.CompanyModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.paperdb.Paper;

public class EditCompany extends AppCompatActivity {

    ActivityEditCompanyBinding binding;
    String json ;
    static int position;
    ArrayList<CompanyModel> arrayList = new ArrayList<>();
    CompanyModel model;
    String  TAG="" , sold_date="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);
        binding = ActivityEditCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        json = getIntent().getStringExtra("company_details");
        position = getIntent().getIntExtra("position",-1);

        if (json != null && !json.equals("")){
            model = new Gson().fromJson(json, CompanyModel.class);
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
        if (!model.name.equals("")){
            binding.etName.setText(model.name);
        }
        if (!model.location.equals("")){
            binding.etLocation.setText(model.location);
        }
        if (!model.description.equals("")){
            binding.etDescription.setText(model.description);
        }
        if (!model.image.equals("")){
            Bitmap bm = BitmapFactory.decodeFile(model.image);
            binding.imgCar.setImageBitmap(bm);
        }
    }
    private void validation(){
        if (binding.etName.getText().toString().equals("")){
            binding.etName.setError("Enter the name first !!!");
        }else if (binding.etLocation.getText().toString().equals("")){
            binding.etLocation.setError("Enter the location first !!!");
        }else if (binding.etDescription.getText().toString().equals("")){
            binding.etDescription.setError("Enter the description first !!!");
        }else {
            addData();
        }
    }
    private void addData(){
        arrayList.clear();
        try {
            ArrayList<CompanyModel> models = Paper.book().read("companyArrayList", new ArrayList<CompanyModel>());
            if (models != null) {
                arrayList.addAll(models);
            } else {
                // Handle the case where no data is found in PaperDB
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        arrayList.remove(arrayList.get(position));
        arrayList.add(new CompanyModel(
                binding.etName.getText().toString(),
                binding.etLocation.getText().toString(),
                binding.etDescription.getText().toString(),
                model.image
        ));
        Toast.makeText(this, "Data updated successfully !!!", Toast.LENGTH_SHORT).show();
        try {
            Paper.book().write("companyArrayList", arrayList);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "addData: " + String.valueOf(arrayList.size()));
        finish();


    }
}