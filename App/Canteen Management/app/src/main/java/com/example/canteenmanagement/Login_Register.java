package com.example.canteenmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Register extends AppCompatActivity {

    private FirebaseAuth fauth;
    private EditText txtEmail,txtPassword;
    private AppCompatButton btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        fauth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                loginUser(email,password);
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Register.this,Register.class));
                finish();
            }
        });

        checkLoginStatus();

    }

    private void checkLoginStatus() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(Login_Register.this, Drawer_Activity.class));
            finish();
        }
    }

    private void loginUser(String email, String password) {

        fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login_Register.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_Register.this, Drawer_Activity.class));
                    finish();
                }
                else{
                    Toast.makeText(Login_Register.this, "Check your Login Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog exitAlert = new AlertDialog.Builder(this).setTitle("Exit").setMessage("Do you want to exit the Application").setIcon(R.drawable.exit_app_icon).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Login_Register.super.onBackPressed();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}