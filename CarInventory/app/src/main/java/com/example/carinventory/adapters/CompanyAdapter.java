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
import com.example.carinventory.databinding.CompanyItemBinding;
import com.example.carinventory.models.CompanyModel;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHoldet> {
    Context context;
    ArrayList<CompanyModel> arrayList;
    ClickListener listener;
    public CompanyAdapter(Context context , ArrayList<CompanyModel> arrayList , ClickListener listener){
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;

    }
    @NonNull
    @Override
    public ViewHoldet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.company_item,parent,false);
        return new ViewHoldet(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldet holder, int position) {
        CompanyModel model = arrayList.get(position);
        holder.binding.txtName.setText(model.name);
        holder.binding.txtLocation.setText(model.location);
        holder.binding.txtDescription.setText(model.description);

        if (model.image == null){
            holder.binding.icLogo.setImageResource(R.drawable.ferrari_logo);
        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(model.image);
            holder.binding.icLogo.setImageBitmap(bitmap);
        }
        holder.binding.companyItemRoot.setOnClickListener(v -> {
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

    public class ViewHoldet extends RecyclerView.ViewHolder {
        CompanyItemBinding binding;
        public ViewHoldet(@NonNull View itemView) {
            super(itemView);
            binding = CompanyItemBinding.bind(itemView);
        }
    }
}
