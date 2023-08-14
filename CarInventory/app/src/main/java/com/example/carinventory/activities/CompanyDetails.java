package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityCompanyDetailsBinding;
import com.example.carinventory.models.CarModel;
import com.example.carinventory.models.CompanyModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CompanyDetails extends AppCompatActivity {

    ActivityCompanyDetailsBinding binding;
    ArrayList<CompanyModel> arrayList = new ArrayList<>();
    String json;
    static int position;
    CompanyModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        binding = ActivityCompanyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        json = getIntent().getStringExtra("company_details");
        position = getIntent().getIntExtra("position",-1);

        try {
            ArrayList<CompanyModel> companyModel = Paper.book().read("carsArrayList", new ArrayList<CompanyModel>());
            if (companyModel != null) {
                arrayList.addAll(companyModel);
            } else {
                // Handle the case where no data is found in PaperDB
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        if (!json.equals("")) {
            model = new Gson().fromJson(json, CompanyModel.class);
            getData();

        }
        binding.icBack.setOnClickListener(v -> {
            finish();
        });

    }
    private void getData(){
        if (!model.name.equals("")){
            binding.txtName.setText(model.name);
        }
        if (!model.location.equals("")){
            binding.txtLocation.setText(model.location);
        }
        if (!model.description.equals("")){
            binding.txtDescription.setText(model.description);
        }
        if (!model.image.equals("")){
            Bitmap bm = BitmapFactory.decodeFile(model.image);
            binding.imgLogo.setImageBitmap(bm);
        }
    }
}