package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityAddCompanyBinding;
import com.example.carinventory.models.CompanyModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.paperdb.Paper;

public class AddCompany extends AppCompatActivity {

    ActivityAddCompanyBinding binding;
    private static final int PICK_IMAGE_REQUEST_CODE = 123;
    static String imagePath , TAG="";
    static Uri imageUri;
    static boolean imgSelected = false;
    ArrayList<CompanyModel> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        binding = ActivityAddCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgPick.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);

        });
        binding.btnAdd.setOnClickListener(v -> {
            validation();
        });
        binding.icBack.setOnClickListener(v -> {
            finish();
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            binding.imgCar.setImageURI(imageUri);
            imgSelected = true;
            // Now you can proceed to save this image to your app's local storage

        }
    }
    private void saveImageToLocalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Save the bitmap to your app's local storage directory
            File directory = new File(getFilesDir(), "images");
            if (!directory.exists()) {
                directory.mkdir();
            }

            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            File file = new File(directory, fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            imagePath = file.getAbsolutePath();

            // Image saved successfully
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors that occur during the process
        }
    }


    private void validation(){
        if (binding.etName.getText().toString().equals("")){
            binding.etName.setError("Enter the company name first !!!");
        }else if (binding.etLocation.getText().toString().equals("")){
            binding.etLocation.setError("Enter the company location first !!!");
        }else if (binding.etDescription.getText().toString().equals("")){
            binding.etDescription.setError("Enter the company description first !!!");
        } else if (!imgSelected) {
            Toast.makeText(this, "Select the image first !!!", Toast.LENGTH_SHORT).show();
        } else {
            if (imageUri != null){
                saveImageToLocalStorage(imageUri);
                addData();
            }else {
                Toast.makeText(this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
            }

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
        arrayList.add(new CompanyModel(binding.etName.getText().toString(),
                binding.etLocation.getText().toString(),
                binding.etDescription.getText().toString(),
                imagePath));
        Toast.makeText(this, "Data added successfully !!!", Toast.LENGTH_SHORT).show();
        try {
            Paper.book().write("companyArrayList", arrayList);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "AddCompany: " + String.valueOf(arrayList.size()));
        finish();


    }
}