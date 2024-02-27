package com.example.canteenmanagement;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Bill_Geneator extends AppCompatActivity {

    private ArrayList<ModelMenuItem> billingItems;
    private RecyclerView recBillItems;
    private ArrayList<Model_GenBill> arrItems = new ArrayList<>();
    private Adapter_GenBill adapter;
    private TextView txtTotalAmount;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_geneator);

        recBillItems = findViewById(R.id.recBillItems);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);

        recBillItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Retrieve the billing items from the intent extras
        billingItems = getIntent().getParcelableArrayListExtra("billingItems");

        // Check if billing items are not null
        if (billingItems != null) {
            // Loop through each billing item and log its details
            for (ModelMenuItem item : billingItems) {
                arrItems.add(new Model_GenBill(item.getFoodName(), item.getFoodPrice(),item.getQuantity()));
                int quantity = item.getQuantity();
                int price = Integer.parseInt(item.getFoodPrice());
                totalAmount = totalAmount+(quantity*price);
                Log.d("BillingItems", "Food Name: " + item.getFoodName() + ", Food Price: " + item.getFoodPrice() + ", Quantity: " + item.getQuantity());
            }
        } else {
            Log.d("BillingItems", "No billing items passed.");
        }

        txtTotalAmount.setText(String.valueOf(totalAmount));
        adapter = new Adapter_GenBill(arrItems);
        recBillItems.setAdapter(adapter);



    }

    @Override
    public void onBackPressed() {
        // Clear the billing items when back button is pressed
        billingItems.clear();
        super.onBackPressed();
    }
}
