package com.example.canteenmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private RecyclerView recMenu;
    private ArrayList<Model_Menu> arrMenuItem = new ArrayList<>();
    private Adapter_Menu adapter;
    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recMenu = view.findViewById(R.id.recMenu);
        recMenu.setLayoutManager(new GridLayoutManager(getContext(),2));
        //////////////////////////////////////////////////////////////////////////////////
        addItems();
        adapter = new Adapter_Menu(arrMenuItem);
        recMenu.setAdapter(adapter);




        //////////////////////////////////////////////////////////////////////////////////
        return view;
    }

    private void addItems() {
        arrMenuItem.add(new Model_Menu(R.drawable.hot_tea,"Hot Drinks"));
        arrMenuItem.add(new Model_Menu(R.drawable.cold_drinks,"Cold Drinks"));
        arrMenuItem.add(new Model_Menu(R.drawable.wafers,"Wafers"));
        arrMenuItem.add(new Model_Menu(R.drawable.breakfast2,"Breakfast"));
        arrMenuItem.add(new Model_Menu(R.drawable.special_food,"Specials"));
    }
}