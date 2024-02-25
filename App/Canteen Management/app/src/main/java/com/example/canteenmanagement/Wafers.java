package com.example.canteenmanagement;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wafers extends AppCompatActivity {

    private RecyclerView recWafers;
    private ArrayList<ModelMenuItem> arrItems= new ArrayList<>();
    private FloatingActionButton btnAddItem;
    private  ImageView digimgNew;
    private Uri uriShow;
    private FirebaseFirestore fstore;
    private AdapterMenuItems adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_drinks);

        recWafers = findViewById(R.id.recColdDrink);
        btnAddItem = findViewById(R.id.btnAddItem);
        recWafers.setLayoutManager(new LinearLayoutManager(Wafers.this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Wafers");
        setSupportActionBar(toolbar);

        retrieveData();



        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItems();
            }
        });



    }

    private void retrieveData() {
        fstore = FirebaseFirestore.getInstance();
        fstore.collection("Wafers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrItems.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String imgUrl = document.getString("imgUrl"); // Retrieve imgUrl instead of imgUri
                                String foodName = document.getString("foodName");
                                String foodPrice = document.getString("foodPrice");
                                ModelMenuItem item = new ModelMenuItem(imgUrl, foodName, foodPrice); // Use imgUrl instead of imgUri
                                Log.d(TAG, "From Firebase   "+foodName+"  "+foodPrice+"    "+imgUrl); // Update log statement
                                arrItems.add(item);
                            }
                            adapter = new AdapterMenuItems(arrItems);
                            recWafers.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Wafers.this, "Unable to Load Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





    private void addItems() {

        TextView digedtFoodName, digedtFoodPrice;
        Button digbtnAdd, digbtnSelect;

        Dialog dialog = new Dialog(Wafers.this);
        dialog.setContentView(R.layout.dialog_add_item);

        digimgNew = dialog.findViewById(R.id.digimgNew);
        digedtFoodName = dialog.findViewById(R.id.digedtFoodName);
        digedtFoodPrice = dialog.findViewById(R.id.digedtFoodPrice);
        digbtnAdd = dialog.findViewById(R.id.digbtnAdd);
        digbtnSelect = dialog.findViewById(R.id.digbtnSelect);
        digimgNew.setImageResource(R.drawable.wafers);

        if (digbtnAdd != null && digbtnSelect != null) {
            digbtnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, 200);
                }
            });
            digbtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri imgUri = uriShow;
                    String foodName = digedtFoodName.getText().toString();
                    String foodPrice = digedtFoodPrice.getText().toString();

                    if(foodName.isEmpty() || foodPrice.isEmpty())
                    {
                        Toast.makeText(Wafers.this, "No Empty Field Allowed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadImageToFirebaseStorage(imgUri, foodName, foodPrice, dialog);
                    }
                }
            });
        }

        dialog.show();
    }

    private void uploadImageToFirebaseStorage(Uri imgUri, String foodName, String foodPrice, Dialog dialog) {
        if (imgUri != null) {
            // Get a reference to the Firebase storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Create a reference to the folder where you want to store the image
            StorageReference imagesRef = storageRef.child("wafers").child(imgUri.getLastPathSegment());

            // Upload the image to Firebase Storage
            imagesRef.putFile(imgUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get the download URL
                        imagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Now you have the download URL, store it in Firestore
                            String imageUrl = uri.toString();
                            storeDataInFirestore(foodName, foodPrice, imageUrl);
                            dialog.dismiss();
                        }).addOnFailureListener(exception -> {
                            // Handle any errors getting the download URL
                            Toast.makeText(Wafers.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                        Toast.makeText(Wafers.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // No image selected
            Toast.makeText(Wafers.this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeDataInFirestore(String foodName, String foodPrice, String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> item = new HashMap<>();
        item.put("foodName", foodName);
        item.put("foodPrice", foodPrice);
        item.put("imgUrl", imageUrl);

        db.collection("Wafers")
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    // DocumentSnapshot added with ID: documentReference.getId()
                    Toast.makeText(Wafers.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    retrieveData();
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Toast.makeText(Wafers.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                uriShow = data.getData();
                String imgDemo = data.getData().toString();
                digimgNew.setImageURI(Uri.parse(imgDemo));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}