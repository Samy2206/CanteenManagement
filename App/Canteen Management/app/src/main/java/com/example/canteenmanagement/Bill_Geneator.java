package com.example.canteenmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Bill_Geneator extends AppCompatActivity {

    private ArrayList<ModelMenuItem> billingItems;
    private RecyclerView recBillItems;
    private ArrayList<Model_GenBill> arrItems = new ArrayList<>();
    private Adapter_GenBill adapter;
    private TextView txtTotalAmount;
    private ImageView btnNext;
    private int totalAmount = 0;
    private TextView txtTotal;
    private Button btnCash, btnOnline, btnPaymentDone;
    private FirebaseFirestore fstore;
    private Map<String, Object> billData = new HashMap<>();
    private int billNumber;
    private String mode, name, dateTime;
    private StringBuilder itemsBuilder = new StringBuilder();
    private EditText edtMoNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_geneator);

        recBillItems = findViewById(R.id.recBillItems);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        fstore = FirebaseFirestore.getInstance();
        btnNext = findViewById(R.id.btnNext);

        recBillItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Retrieve the billing items from the intent extras
        billingItems = getIntent().getParcelableArrayListExtra("billingItems");

        // Check if billing items are not null
        if (billingItems != null) {
            // Loop through each billing item and calculate total amount
            for (ModelMenuItem item : billingItems) {
                arrItems.add(new Model_GenBill(item.getFoodName(), item.getFoodPrice(), item.getQuantity()));
                int quantity = item.getQuantity();
                int price = Integer.parseInt(item.getFoodPrice());
                totalAmount += quantity * price;
                String Items = item.getFoodName()+"---"+String.valueOf(item.getQuantity())+"---"+String.valueOf(totalAmount)+"\n";
                itemsBuilder.append(Items);
                Log.d(TAG, "onCreate: "+itemsBuilder);
            }
        }

        txtTotalAmount.setText(String.valueOf(totalAmount));
        adapter = new Adapter_GenBill(arrItems);
        recBillItems.setAdapter(adapter);

        // Retrieve or generate a new bill number for the current day
        billNumber = getBillNumberForCurrentDay();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display payment dialog
                showPaymentDialog();
            }
        });
    }

    private void showPaymentDialog() {
        Dialog dialog = new Dialog(Bill_Geneator.this);
        dialog.setContentView(R.layout.dialog_payment);
        EditText edtBillName;

        txtTotal = dialog.findViewById(R.id.txtTotal);
        btnCash = dialog.findViewById(R.id.btnCash);
        btnOnline = dialog.findViewById(R.id.btnOnline);
        edtBillName = dialog.findViewById(R.id.edtBillName);
        edtMoNo = dialog.findViewById(R.id.edtMoNo);

        txtTotal.setText(String.valueOf(totalAmount));
        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set billing mode to Cash
                mode = "Cash";
                name = edtBillName.getText().toString();
                dateTime = getCurrentDateTime();
                uploadBillToFirestore();
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogOnline = new Dialog(Bill_Geneator.this);
                dialogOnline.setContentView(R.layout.dialog_online);
                btnPaymentDone = dialogOnline.findViewById(R.id.btnPaymentDone);
                btnPaymentDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Set billing mode to Online
                        mode = "Online";
                        name = edtBillName.getText().toString();
                        dateTime = getCurrentDateTime();
                        uploadBillToFirestore();
                    }
                });
                dialogOnline.show();

            }
        });

        dialog.show();
    }

    private void openMsgDialog() {
        if (!isFinishing()) { // Check if the activity is not finishing
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Bill_Geneator.this);
            alertDialog.setTitle("SMS BILL")
                    .setMessage("Do you want to SMS bill")
                    .setIcon(R.drawable.rupee)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendBillSMS(); // Move the SMS sending logic to a separate method
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    private void sendBillSMS() {
        String mono = "+91" + edtMoNo.getText().toString();
        String msg = String.valueOf(itemsBuilder);

        Intent iSms = new Intent();
        iSms.setAction(Intent.ACTION_SENDTO);
        iSms.setData(Uri.parse("smsto:" + Uri.encode(mono))); //smsto: -> for sms
        iSms.putExtra("sms_body", msg); //sms_body -> key to send sms data
        startActivity(iSms);
        finish();
    }


    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    private void uploadBillToFirestore() {
        // Prepare bill data to be stored in Firestore
        billData.put("billNumber", billNumber);
        billData.put("billingMode", mode);
        billData.put("billAmount", totalAmount);
        billData.put("billName", name);
        billData.put("dateTime", dateTime);

        // Create a new map to store bill items
        Map<String, Object> billItemsMap = new HashMap<>();
        for (int i = 0; i < billingItems.size(); i++) {
            ModelMenuItem item = billingItems.get(i);
            Map<String, Object> itemData = new HashMap<>();
            itemData.put("foodName", item.getFoodName());
            itemData.put("quantity", item.getQuantity());
            int totalPrice = Integer.parseInt(item.getFoodPrice()) * item.getQuantity();
            itemData.put("totalPrice", totalPrice);
            billItemsMap.put("item_" + i, itemData);
        }
        // Add the bill items map to the bill data
        billData.put("billItems", billItemsMap);

        // Write bill data to Firestore
        fstore.collection("Bills")
                .add(billData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Bill_Geneator.this, "Bill added to database with bill number: " + billNumber, Toast.LENGTH_SHORT).show();
                    // Increment the bill number and save it to SharedPreferences
                    incrementBillNumber();
                    openMsgDialog();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Bill_Geneator.this, "Failed to add bill to database", Toast.LENGTH_SHORT).show();
                });
    }

    private int getBillNumberForCurrentDay() {
        // Retrieve the last saved day and bill number from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("BillPrefs", Context.MODE_PRIVATE);
        int lastSavedDay = sharedPref.getInt("lastSavedDay", -1);
        int lastBillNumber = sharedPref.getInt("billNumber", 1);

        // Get the current day
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Check if the current day is different from the last saved day
        if (currentDay != lastSavedDay) {
            // Reset the bill number for the new day
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("lastSavedDay", currentDay);
            editor.putInt("billNumber", 1); // Reset the bill number to 1 for the new day
            editor.apply();
            return 1; // Return the new bill number for the new day
        } else {
            // Return the last saved bill number for the current day
            return lastBillNumber;
        }
    }

    private void incrementBillNumber() {
        // Increment the bill number and save it to SharedPreferences
        billNumber++;
        SharedPreferences sharedPref = getSharedPreferences("BillPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("billNumber", billNumber);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        // Clear the billing items when back button is pressed
        billingItems.clear();
        super.onBackPressed();
    }
}
