package com.example.canteenmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private Button btnDelBills,btnDelHot,btnDelCold,btnDelWafers,btnDelBreakfast,btnDelSpecial;

    private static final String TAG = "AdminActivity";
    private FirebaseFirestore firestore;
    private ArrayList<ModelAll> modelAllList = new ArrayList<>();
   private TextView txtDate,txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firestore = FirebaseFirestore.getInstance();
        btnDelBills = findViewById(R.id.btnDelBills);
        btnDelHot = findViewById(R.id.btnDelHot);
        btnDelCold = findViewById(R.id.btnDelCold);
        btnDelWafers = findViewById(R.id.btnDelWafers);
        btnDelBreakfast = findViewById(R.id.btnDelBreakfast);
        btnDelSpecial = findViewById(R.id.btnDelSecial);
        txtDate = findViewById(R.id.txtDate);
        txtTotal = findViewById(R.id.txtTotal);

        // Get the current date
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(currentDate);

        // Define the query to retrieve bills for the current date
        Query query = firestore.collection("Bills").whereGreaterThanOrEqualTo("dateTime", dateString);

        // Execute the query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Map to store total amount for each date
                    Map<String, Integer> totalAmountMap = new HashMap<>();

                    // Iterate over each document to calculate total amount for each date
                    for (DocumentSnapshot document : task.getResult()) {
                        String billDate = document.getString("dateTime").split(" ")[0]; // Extract date from dateTime
                        int billAmount = document.getLong("billAmount").intValue(); // Extract bill amount
                        // Update total amount for the date in the map
                        if (totalAmountMap.containsKey(billDate)) {
                            int currentTotal = totalAmountMap.get(billDate);
                            totalAmountMap.put(billDate, currentTotal + billAmount);
                        } else {
                            totalAmountMap.put(billDate, billAmount);
                        }
                    }

                    // Convert the totalAmountMap data to ModelAll objects and add them to the list
                    for (Map.Entry<String, Integer> entry : totalAmountMap.entrySet()) {
                        txtTotal.setText(String.valueOf(entry.getValue()));
                        txtTotal.setText(String.valueOf(entry.getValue()));
                        Log.d(TAG, "onComplete: "+modelAllList);
                    }



                    // Log total amount for each date
                    for (ModelAll modelAll : modelAllList) {
                        Log.d(TAG, "Total amount of bills for " + modelAll.getDate() + ": " + modelAll.getTotalAmount());
                        // You can use the total amount as needed for each date
                    }
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        btnDelHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "ColdDrinks" collection
                firestore.collection("HotDrinks")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Toast.makeText(AdminActivity.this, "All hot drinks deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "All hot drinks deleted successfully");

                                } else {
                                    Log.e(TAG, "Error deleting hot drinks: ", task.getException());
                                }
                            }
                        });
            }
        });
        btnDelSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "ColdDrinks" collection
                firestore.collection("special")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Toast.makeText(AdminActivity.this, "All Special items are deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "All Special items are deleted successfully");

                                } else {
                                    Log.e(TAG, "Error deleting Special Items: ", task.getException());
                                }
                            }
                        });
            }
        });
        btnDelCold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "ColdDrinks" collection
                firestore.collection("ColdDrinks")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Toast.makeText(AdminActivity.this, "All Cold Drink items are deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "All Cold Drink items are deleted successfully");

                                } else {
                                    Log.e(TAG, "Error deleting Cold Drinks Items: ", task.getException());
                                }
                            }
                        });
            }
        });
        btnDelWafers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "ColdDrinks" collection
                firestore.collection("Wafers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Toast.makeText(AdminActivity.this, "All Wafer items are deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "All Wafer items are deleted successfully");

                                } else {
                                    Log.e(TAG, "Error Wafer Special Items: ", task.getException());
                                }
                            }
                        });
            }
        });
        btnDelBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "ColdDrinks" collection
                firestore.collection("breakfast")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Toast.makeText(AdminActivity.this, "All Special items are deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "All Special items are deleted successfully");

                                } else {
                                    Log.e(TAG, "Error deleting Special Items: ", task.getException());
                                }
                            }
                        });
            }
        });



        btnDelBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all documents in the "Bills" collection
                firestore.collection("Bills")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterate through each document and delete it
                                    for (DocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                    Log.d(TAG, "All bills deleted successfully");

                                    // After deleting bills, clear the shared preference
                                    resetSharedPreferences();

                                    // After resetting shared preference, you may want to update the UI or perform any other action
                                    // For example, you can clear the list of ModelAll objects
                                    modelAllList.clear();
                                } else {
                                    Log.e(TAG, "Error deleting bills: ", task.getException());
                                }
                            }
                        });
            }
        });
    }
    private void resetSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("BillPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear(); // Clear all data in the shared preference
        editor.apply();
    }

}
