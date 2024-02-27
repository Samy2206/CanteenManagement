package com.example.canteenmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_GenBill extends RecyclerView.Adapter<Adapter_GenBill.ViewHolder> {
    private ArrayList<Model_GenBill> arrItems = new ArrayList<>();
    private int itemPrice;

    public Adapter_GenBill(ArrayList<Model_GenBill> arrItems) {
        this.arrItems = arrItems;
    }

    @NonNull
    @Override
    public Adapter_GenBill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_GenBill.ViewHolder holder, int position) {
        holder.txtName.setText(arrItems.get(position).getFoodName());
        holder.txtQuantity.setText(String.valueOf(arrItems.get(position).getQuantity()));
        itemPrice = Integer.parseInt(String.valueOf(arrItems.get(position).getQuantity()))*Integer.parseInt(arrItems.get(position).getFoodPrice());
        holder.txtTotal.setText(String.valueOf(itemPrice));

    }

    @Override
    public int getItemCount() {
        return arrItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtTotal,txtQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtTotal = itemView.findViewById(R.id.txtTotal);


        }
    }
}
