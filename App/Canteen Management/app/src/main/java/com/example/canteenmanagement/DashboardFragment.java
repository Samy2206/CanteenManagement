package com.example.canteenmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class DashboardFragment extends Fragment {
    private ArrayList<Model_Bill_Content> arrBill = new ArrayList<>();
    private RecyclerView recBills;
    private Adapter_Bill adapterBill;
    // Reference to Firestore instance
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // Reference to the "Bills" collection
    private CollectionReference billsCollection = firestore.collection("Bills");

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recBills = view.findViewById(R.id.recBills);
        recBills.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllBills();

        adapterBill = new Adapter_Bill(arrBill,getContext());
        recBills.setAdapter(adapterBill);

        return view;
    }

    private void getAllBills() {
        billsCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                // Retrieve bill data
                String billNumber = String.valueOf(documentSnapshot.getLong("billNumber"));
                String billingMode = documentSnapshot.getString("billingMode");
                int billAmount = documentSnapshot.getLong("billAmount").intValue();
                String billName = documentSnapshot.getString("billName");
                String dateTime = documentSnapshot.getString("dateTime");

                Log.d("Bill_Data", "Bill Number: " + billNumber);
                Log.d("Bill_Data", "Billing Mode: " + billingMode);
                Log.d("Bill_Data", "Bill Amount: " + billAmount);
                Log.d("Bill_Data", "Bill Name: " + billName);
                Log.d("Bill_Data", "Date Time: " + dateTime);

                // Retrieve bill items
                StringBuilder itemsBuilder = new StringBuilder();
                Map<String, Object> billItems = (Map<String, Object>) documentSnapshot.get("billItems");
                if (billItems != null) {
                    for (Map.Entry<String, Object> entry : billItems.entrySet()) {
                        Map<String, Object> itemData = (Map<String, Object>) entry.getValue();
                        String foodName = (String) itemData.get("foodName");
                        int quantity = ((Long) itemData.get("quantity")).intValue();
                        int totalPrice = ((Long) itemData.get("totalPrice")).intValue();

                        String item = foodName + "----" + quantity + "----" + totalPrice + "\n";
                        itemsBuilder.append(item);

                        Log.d("Bill_Data", "Food Name: " + foodName);
                        Log.d("Bill_Data", "Quantity: " + quantity);
                        Log.d("Bill_Data", "Total Price: " + totalPrice);
                    }
                    String items = itemsBuilder.toString();
                    Log.d(TAG, "BillItems: " + items);

                    arrBill.add(new Model_Bill_Content(billNumber, billingMode, billAmount, billName, dateTime, items));
                } else {
                    Log.d("Bill_Data", "No bill items found for bill number: " + billNumber);
                }
            }
            // Notify the adapter after adding all bills
            adapterBill.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle failure
            Log.e("Bill_Data", "Failed to retrieve bills: " + e.getMessage());
        });
    }
}
