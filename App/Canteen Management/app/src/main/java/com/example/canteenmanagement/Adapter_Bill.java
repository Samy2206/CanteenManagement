package com.example.canteenmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Bill extends RecyclerView.Adapter<Adapter_Bill.ViewHolder> {
    private ArrayList<Model_Bill_Content> arrBills = new ArrayList<>();

    public Adapter_Bill(ArrayList<Model_Bill_Content> arrBills, Context context) {
        this.arrBills = arrBills;
    }

    @NonNull
    @Override
    public Adapter_Bill.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Bill.ViewHolder holder, int position) {
        Model_Bill_Content billContent = arrBills.get(position);

        holder.txtBillName.setText(billContent.getBillName());
        holder.txtBillNumber.setText(billContent.getBillNumber());
        holder.txtBillAmount.setText("Rs   "+String.valueOf(billContent.getBillAmount()));
        holder.txtBillMode.setText(billContent.getBillingMode());
        holder.txtDateTime.setText(billContent.getDateTime());
        holder.txtBillItems.setText(billContent.getItems());

    }

    @Override
    public int getItemCount() {
        return arrBills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBillName, txtBillNumber, txtBillAmount, txtBillMode, txtDateTime,txtBillItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBillName = itemView.findViewById(R.id.txtBillName);
            txtBillNumber = itemView.findViewById(R.id.txtBillNumber);
            txtBillAmount = itemView.findViewById(R.id.txtBillAmount);
            txtBillMode = itemView.findViewById(R.id.txtBillMode);
            txtDateTime = itemView.findViewById(R.id.txtBillDateTime);
            txtBillItems = itemView.findViewById(R.id.txtBillItems);
        }
    }
}
