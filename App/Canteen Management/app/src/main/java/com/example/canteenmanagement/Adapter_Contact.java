package com.example.canteenmanagement;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_Contact extends RecyclerView.Adapter<Adapter_Contact.ViewHolder> {
    ArrayList<Model_Contact> arrContact = new ArrayList<>();
    ContactActivity context;

    public Adapter_Contact(ArrayList<Model_Contact> arrContact, ContactActivity context) {
        this.arrContact = arrContact;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Contact.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Contact.ViewHolder holder, int position) {
        holder.txtName.setText(arrContact.get(position).getName());
        holder.txtMoNumber.setText(arrContact.get(position).getMoNumber());

        // Set click listener for making a phone call
        holder.llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = arrContact.get(position).getMoNumber();
                makePhoneCall(phoneNumber);
            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        // Check if the app has permission to make a phone call
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission to make a phone call
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }

        // Create an intent with the ACTION_CALL action and the phone number to dial
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        // Check if there is an activity that can handle the intent (phone dialer)
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            // Start the activity to make the phone call
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return arrContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtMoNumber;
        private LinearLayout llContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMoNumber = itemView.findViewById(R.id.txtMoNumber);
            llContact = itemView.findViewById(R.id.llContact);
        }
    }
}
