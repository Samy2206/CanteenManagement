package com.example.canteenmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Billing extends RecyclerView.Adapter<Adapter_Billing.ViewHolder> {
    private ArrayList<ModelMenuItem> arrayItems = new ArrayList<>();
    private OnBillItemClickListener mListener;

    public Adapter_Billing(ArrayList<ModelMenuItem> arrayItems, OnBillItemClickListener listener) {
        this.arrayItems = arrayItems;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMenuItem menuItem = arrayItems.get(position);

        holder.txtFoodName.setText(menuItem.getFoodName());
        holder.txtFoodPrice.setText(menuItem.getFoodPrice());
        holder.txtFoodQuantity.setText(String.valueOf(menuItem.getQuantity()));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = menuItem.getQuantity() + 1;
                menuItem.setQuantity(quantity);
                holder.txtFoodQuantity.setText(String.valueOf(quantity));
                mListener.onBillItemClick(menuItem, quantity);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = menuItem.getQuantity() - 1;
                if (quantity >= 0) {
                    menuItem.setQuantity(quantity);
                    holder.txtFoodQuantity.setText(String.valueOf(quantity));
                    mListener.onBillItemClick(menuItem, quantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFoodName, txtFoodPrice, txtFoodQuantity;
        private ImageView btnAdd, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtFoodQuantity = itemView.findViewById(R.id.txtFoodQuantity);
            btnRemove = itemView.findViewById(R.id.btnRemoveFood);
            btnAdd = itemView.findViewById(R.id.btnAddFood);
        }
    }

    public interface OnBillItemClickListener {
        void onBillItemClick(ModelMenuItem item, int quantity);
    }
}
