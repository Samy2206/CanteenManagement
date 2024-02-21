package com.example.canteenmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Menu extends RecyclerView.Adapter<Adapter_Menu.ViewHolder>{
    private ArrayList<Model_Menu> arrMenuItem = new ArrayList<>();

    public Adapter_Menu(ArrayList<Model_Menu> arrMenuItem) {
        this.arrMenuItem = arrMenuItem;
    }

    @NonNull
    @Override
    public Adapter_Menu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Menu.ViewHolder holder, int position) {

        holder.imgMenuItem.setImageResource(arrMenuItem.get(position).getImg());
        holder.txtItemName.setText(arrMenuItem.get(position).getItemName());

    }

    @Override
    public int getItemCount() {
        return arrMenuItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenuItem;
        TextView txtItemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMenuItem = itemView.findViewById(R.id.imgMenuItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);

        }
    }
}
