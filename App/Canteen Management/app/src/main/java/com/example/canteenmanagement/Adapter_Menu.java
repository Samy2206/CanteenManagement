package com.example.canteenmanagement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenmanagement.Model_Menu;
import com.example.canteenmanagement.R;

import java.util.ArrayList;

public class Adapter_Menu extends RecyclerView.Adapter<Adapter_Menu.ViewHolder>{
    private ArrayList<Model_Menu> arrMenuItem = new ArrayList<>();
    private OnItemClickListener mListener; // Listener variable

    // Interface for item click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adapter_Menu(ArrayList<Model_Menu> arrMenuItem) {
        this.arrMenuItem = arrMenuItem;
    }

    @NonNull
    @Override
    public Adapter_Menu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Menu.ViewHolder holder, int position) {
        holder.imgMenuItem.setImageResource(arrMenuItem.get(position).getImg());
        holder.txtItemName.setText(arrMenuItem.get(position).getItemName());

        // Set click listener for each item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
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
