package com.example.carinventory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.carinventory.R;
import com.example.carinventory.databinding.ActivityAddCarBinding;
import com.example.carinventory.models.CarModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.paperdb.Paper;

public class AddCar extends AppCompatActivity {

    ActivityAddCarBinding binding;
    private static final int PICK_IMAGE_REQUEST_CODE = 123;
    static String imagePath ="", sold_date="";
    ArrayList<CarModel> arrayList = new ArrayList<>();
    String TAG = "AddCar";
    static Uri imageUri;
    static boolean imageSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_color_bg));

        binding.icBack.setOnClickListener(v -> {
            finish();
        });


        binding.imgPick.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);

        });
        binding.btnAdd.setOnClickListener(v -> {
            validation();
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            // Now you can proceed to save this image to your app's local storage
            binding.imgCar.setImageURI(imageUri);
            imageSelected = true;
        }else {
            Toast.makeText(this, "No image selected !!!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
        } else if (!imageSelected){
            Toast.makeText(this, "Select the image first !!!", Toast.LENGTH_SHORT).show();
        }else {
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
        arrayList.add(new CarModel(binding.etCompany.getText().toString(),
                binding.etModel.getText().toString(),
                binding.etCondition.getText().toString(),
                binding.etEngineCylinder.getText().toString(),
                binding.etYear.getText().toString(),
                binding.etNoOffDoors.getText().toString(),
                binding.etPrice.getText().toString(),
                binding.etColor.getText().toString(),
                imagePath,
                false,
                sold_date
        ));
        Toast.makeText(this, "Data added successfully !!!", Toast.LENGTH_SHORT).show();
        Paper.book().write("carsArrayList", arrayList);
        Log.d(TAG, "addData: " + String.valueOf(arrayList.size()));
        finish();



    }
}