package com.example.canteenmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAllTotal extends RecyclerView.Adapter<AdapterAllTotal.ViewHolder> {
    private ArrayList<ModelAll> allTotal = new ArrayList<>();

    public AdapterAllTotal(ArrayList<ModelAll> allTotal) {
        this.allTotal = allTotal;
    }

    @NonNull
    @Override
    public AdapterAllTotal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mode_all,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllTotal.ViewHolder holder, int position) {

        holder.txtDate.setText(String.valueOf(allTotal.get(position).getDate()));
        holder.txtTotal.setText(String.valueOf(allTotal.get(position).getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return allTotal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate,txtTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTotal = itemView.findViewById(R.id.txtTotal);

        }
    }
}
