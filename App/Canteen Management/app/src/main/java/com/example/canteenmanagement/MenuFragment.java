package com.example.canteenmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        recMenu.setLayoutManager(new GridLayoutManager(getContext(), 2));

        addItems();

        adapter = new Adapter_Menu(arrMenuItem);
        recMenu.setAdapter(adapter);

        adapter.setOnItemClickListener(new Adapter_Menu.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                       startActivity(new Intent(getActivity(), HotDrinks.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), ColdDrinks.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), Wafers.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), Breakfast.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), Specials.class));
                        break;
                }
            }
        });

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
