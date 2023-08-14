package com.example.carinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.carinventory.activities.AddCarA;
import com.example.carinventory.activities.AddCompanyA;
import com.example.carinventory.databinding.ActivityMainBinding;
import com.example.carinventory.fragments.HomeF;
import com.example.carinventory.fragments.StatsF;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final int REQUEST_CODE_PERMISSION = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_color_bg));

        loadFragment(new HomeF(),0);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }


        binding.homeItem.setOnClickListener(v -> {
            loadFragment(new HomeF(),1);
            binding.navHome.setVisibility(View.GONE);
            binding.navHomeActive.setVisibility(View.VISIBLE);
            binding.navStatActive.setVisibility(View.GONE);
            binding.navStat.setVisibility(View.VISIBLE);
            binding.txtHome.setTextColor(getColor(R.color.my_color));
            binding.txtStat.setTextColor(getColor(R.color.text_color));
        });

        binding.statItem.setOnClickListener(v -> {
            loadFragment(new StatsF(),1);
            binding.navStat.setVisibility(View.GONE);
            binding.navStatActive.setVisibility(View.VISIBLE);
            binding.navHomeActive.setVisibility(View.GONE);
            binding.navHome.setVisibility(View.VISIBLE);
            binding.txtHome.setTextColor(getColor(R.color.text_color));
            binding.txtStat.setTextColor(getColor(R.color.my_color));
        });

        binding.addItem.setOnClickListener(v -> {
            TextView txt_car , txt_company , txt_cancel;

            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialogue_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            txt_car= dialog.findViewById(R.id.txt_car);
            txt_company= dialog.findViewById(R.id.txt_company);
            txt_cancel = dialog.findViewById(R.id.txt_cancel);

            txt_car.setOnClickListener(v1 -> {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, AddCarA.class));
            });

            txt_company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this, AddCompanyA.class));
                }
            });
            txt_cancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialog.setCancelable(true);

            dialog.show();
        });


    }
    private void loadFragment(Fragment fragment , int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.container,fragment);
        }else {
            ft.replace(R.id.container,fragment);
        }
        ft.commit();
    }
}