package com.example.canteenmanagement;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView recContacts;
    private FloatingActionButton btnAddContact;
    private Adapter_Contact adapterContact;
    private ArrayList<Model_Contact> arrContact = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        firestore = FirebaseFirestore.getInstance();
        recContacts = findViewById(R.id.recContacts);
        btnAddContact = findViewById(R.id.btnAddContact);

        recContacts.setLayoutManager(new LinearLayoutManager(ContactActivity.this));
        adapterContact = new Adapter_Contact(arrContact,this);
        recContacts.setAdapter(adapterContact);

        retrievedata();

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            EditText edtName, edtMoNo;
            Button btnAddContactDialog;

            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ContactActivity.this);
                dialog.setContentView(R.layout.dialog_add_contact);

                edtName = dialog.findViewById(R.id.edtName);
                edtMoNo = dialog.findViewById(R.id.edtMoNo);
                btnAddContactDialog = dialog.findViewById(R.id.btnAddContactDialog);

                btnAddContactDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString().trim();
                        String moNo = "+91"+edtMoNo.getText().toString().trim();
                        if (!name.isEmpty() && !moNo.isEmpty()) {
                            // Add the contact to Firestore
                            addContactToFirestore(name, moNo);
                            retrievedata();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ContactActivity.this, "Name and mobile number cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void addContactToFirestore(String name, String moNo) {
        Map<String, Object> contact = new HashMap<>();
        contact.put("name", name);
        contact.put("mobileNumber", moNo);

        firestore.collection("Contacts")
                .add(contact)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ContactActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                    retrievedata(); // Refresh the data after adding the contact
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ContactActivity.this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                });
    }

    private void retrievedata() {
        firestore.collection("Contacts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        arrContact.clear(); // Clear the existing list to avoid duplicates
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String mobileNumber = document.getString("mobileNumber");
                            arrContact.add(new Model_Contact(name,mobileNumber));
                        }
                        adapterContact.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ContactActivity.this, "Failed to retrieve contacts", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
