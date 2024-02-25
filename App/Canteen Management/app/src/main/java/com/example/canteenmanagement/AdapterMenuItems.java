package com.example.canteenmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterMenuItems extends RecyclerView.Adapter<AdapterMenuItems.ViewHolder> {

    private ArrayList<ModelMenuItem> arrItems = new ArrayList<>();

    public AdapterMenuItems(ArrayList<ModelMenuItem> arrItems) {
        this.arrItems = arrItems;
    }

    @NonNull
    @Override
    public AdapterMenuItems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detailed_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMenuItems.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(arrItems.get(position).getImgUrl())
                .into(holder.imgFood);
//        holder.imgFood.setImageURI(arrItems.get(position).getImgUri());
        holder.txtFoodName.setText(arrItems.get(position).getFoodName());
        holder.txtFoodPrice.setText(arrItems.get(position).getFoodPrice());
    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtFoodName,txtFoodPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
        }
    }
}
