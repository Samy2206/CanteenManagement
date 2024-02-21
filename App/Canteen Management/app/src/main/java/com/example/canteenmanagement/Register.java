package com.example.canteenmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private EditText txtName,txtEmail,txtPassword,txtPhoneNo;
    private AppCompatButton btnRegister;
    private Spinner spinnerView;
    private ArrayList<String> arrPosition = new ArrayList<>();

    String txtPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtPhoneNo = findViewById(R.id.txtPhoneNo);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        btnRegister = findViewById(R.id.btnRegister); // Corrected typo here
        spinnerView = findViewById(R.id.spinnerView);

        setPositions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrPosition);
        spinnerView.setAdapter(adapter);
        spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtPosition = arrPosition.get(position); // Get selected position from spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtPosition = ""; // Handle case when nothing is selected
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String phoneNo = txtPhoneNo.getText().toString();
                String position = txtPosition;

                if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password Length too Short", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Register.this, "Name Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phoneNo)) {
                    Toast.makeText(Register.this, "Phone Number Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (position.equals("---Select---")) {
                    Toast.makeText(Register.this, "Please select a position", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password, name, phoneNo, position);
                }
            }
        });


    }

    private void setPositions() {
        arrPosition.add("---Select---");
        arrPosition.add("Manager");
        arrPosition.add("Cook");
        arrPosition.add("Sweeper");
        arrPosition.add("Waiter");
    }

    private void registerUser(String email, String password, String name, String phoneNo,String position) {
        fauth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String userID = fauth.getUid();
                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                DocumentReference ref = fstore.collection("user").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("name",name);
                user.put("email",email);
                user.put("phoneno",phoneNo);
                user.put("position",position);
                ref.set(user);
                startActivity(new Intent(Register.this, Drawer_Activity.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
