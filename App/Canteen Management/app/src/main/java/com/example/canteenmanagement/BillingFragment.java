// BillingFragment.java
package com.example.canteenmanagement;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BillingFragment extends Fragment implements Adapter_Billing.OnBillItemClickListener {
    private RecyclerView recBillHot, recBillCold, recBillBreakfast, recBillWafers, recBillSpecial;
    private FloatingActionButton btnGenBill;
    private ArrayList<ModelMenuItem> billingItems;
    private FirebaseFirestore fstore;

    public BillingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        recBillHot = view.findViewById(R.id.recBillHot);
        recBillCold = view.findViewById(R.id.recBillCold);
        recBillBreakfast = view.findViewById(R.id.recBillBreakfast);
        recBillWafers = view.findViewById(R.id.recBillWafers);
        recBillSpecial = view.findViewById(R.id.recBillSpecial);
        btnGenBill = view.findViewById(R.id.btnGenBill);

        recBillHot.setLayoutManager(new LinearLayoutManager(getContext()));
        recBillCold.setLayoutManager(new LinearLayoutManager(getContext()));
        recBillBreakfast.setLayoutManager(new LinearLayoutManager(getContext()));
        recBillWafers.setLayoutManager(new LinearLayoutManager(getContext()));
        recBillSpecial.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        btnGenBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateBill();
            }
        });

        return view;
    }

    private void generateBill() {
        Intent intent = new Intent(getContext(), Bill_Geneator.class);
        intent.putParcelableArrayListExtra("billingItems", billingItems);
        startActivity(intent);
    }

    private void loadData() {
        fstore = FirebaseFirestore.getInstance();

        // Load HotDrinks data
        loadMenuItems("HotDrinks", recBillHot);

        // Load ColdDrinks data
        loadMenuItems("ColdDrinks", recBillCold);

        // Load Breakfast data
        loadMenuItems("breakfast", recBillBreakfast);

        // Load Wafers data
        loadMenuItems("Wafers", recBillWafers);

        // Load Special data
        loadMenuItems("special", recBillSpecial);
    }

    private void loadMenuItems(String collectionName, RecyclerView recyclerView) {
        fstore.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ModelMenuItem> menuItems = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String foodName = document.getString("foodName");
                                String foodPrice = document.getString("foodPrice");
                                ModelMenuItem item = new ModelMenuItem(foodName, foodPrice);
                                menuItems.add(item);
                            }
                            Adapter_Billing adapter = new Adapter_Billing(menuItems, BillingFragment.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Unable to load data for " + collectionName, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBillItemClick(ModelMenuItem item, int quantity) {
        if (quantity > 0) {
            if (billingItems == null) {
                billingItems = new ArrayList<>();
            }
            // Check if the item already exists in the billing list
            boolean found = false;
            for (ModelMenuItem billingItem : billingItems) {
                if (billingItem.getFoodName().equals(item.getFoodName())) {
                    // Update the quantity if the item already exists
                    found = true;
                    billingItem.setQuantity(quantity);
                    break;
                }
            }
            // If the item is not found, add it to the billing list
            if (!found) {
                billingItems.add(new ModelMenuItem(item.getFoodName(), item.getFoodPrice(), quantity));
            }
        }
    }



}
