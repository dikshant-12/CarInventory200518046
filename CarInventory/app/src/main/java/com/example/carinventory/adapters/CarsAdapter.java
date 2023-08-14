package com.example.carinventory.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carinventory.ClickListener;
import com.example.carinventory.R;
import com.example.carinventory.databinding.CarItemBinding;
import com.example.carinventory.models.CarModel;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    Context context;
    ArrayList<CarModel> arrayList;
    ClickListener listener;
    public CarsAdapter(Context context , ArrayList<CarModel> arrayList, ClickListener listener){
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarModel carModel = arrayList.get(position);
        holder.binding.txtCompany.setText(carModel.make);
        holder.binding.txtModel.setText(carModel.model);
        holder.binding.txtPrice.setText(carModel.price);

        if (carModel.image == null){
            holder.binding.imgCar.setImageResource(R.drawable.car);
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(carModel.image);
            holder.binding.imgCar.setImageBitmap(bitmap);
        }
        if (carModel.is_sold){
            holder.binding.txtIsSold.setText("Sold Out");
            holder.binding.txtIsSold.setTextColor(context.getColor(R.color.my_color));
            holder.binding.icEdit.setVisibility(View.GONE);
        }
        holder.binding.carItemRoot.setOnClickListener(v -> {
            listener.onClick(position,1);
        });
        holder.binding.icEdit.setOnClickListener(v -> {
            listener.onClick(position,2);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CarItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CarItemBinding.bind(itemView);
        }
    }
}
