package com.example.assignment2.adminPanel.activities.adminModuleActivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.assignment2.ModelClasses.PersonItemModel;
import com.example.assignment2.R;
import com.example.assignment2.UpdateProfileActivity;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class UpdateItemActivity extends AppCompatActivity {
    private EditText name, email;
    private AppCompatButton updateButton, homeButton;
    private ImageView itemUpdateImg;
    private Uri imageUri;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String profileLink;
    private String itemKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        name = findViewById(R.id.itemNameUpdate);
        email = findViewById(R.id.itemPriceUpdate);
        updateButton = findViewById(R.id.updateItemButton);
        homeButton = findViewById(R.id.backHomeButton);
        itemUpdateImg = findViewById(R.id.itemUpdateImg);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        itemKey = intent.getStringExtra("key");


        getUserData(itemKey);
        itemUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                imagePickerActivityResult.launch(galleryIntent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameString = name.getText().toString().trim();
                String itemPriceString = email.getText().toString().trim();

                if (itemNameString.isEmpty() || itemPriceString.isEmpty()) {
                    Toast.makeText(UpdateItemActivity.this, "Field's are empty", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage(itemNameString, itemPriceString, imageUri, itemKey);
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateItemActivity.this,UpdateListActivity.class));
            }
        });

    }

    private void getUserData(String itemKey) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Inventory Management System").child("Items");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AddItemModel userDetail = dataSnapshot.getValue(AddItemModel.class);

                        // Check if the userDetail is not null
                        if (userDetail != null && userDetail.getItemKey().equals(itemKey)) {
                            // Update UI with user details
                            String names = userDetail.getItemName();
                            String emails = userDetail.getItemPrice();
                            String imageUrl = userDetail.getImageUrl();

                            name.setText(names);
                            email.setText(emails);

                            Glide.with(getApplicationContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.productphoto)
                                    .into(itemUpdateImg);

                            Toast.makeText(UpdateItemActivity.this, "User Found", Toast.LENGTH_SHORT).show();
                            break; // Break out of the loop once the item is found
                        }
                    }
                } else {
                    // Handle the case where userId does not exist in the database
                    Toast.makeText(UpdateItemActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateItemActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(String itemName, String itemPrice, Uri imagePathUri, String itemKey) {
        ProgressDialog pd = new ProgressDialog(UpdateItemActivity.this);
        pd.setMessage("Uploading");
        pd.show();
        if (imageUri != null) {
            storageReference = firebaseStorage.getReference();
            StorageReference imageRef = storageReference.child("Item Images").child(System.currentTimeMillis() + "_" + imagePathUri.getLastPathSegment());

            imageRef.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Image upload successful, now retrieve download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        profileLink = uri.toString();
                        updateProfile(itemName, itemPrice, profileLink, itemKey);
                        pd.dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(UpdateItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    });
                } else {
                    Toast.makeText(UpdateItemActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }

    private void updateProfile(String name, String email, String imageUrl, String itemKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Inventory Management System").child("Items").child(itemKey);

        Map<String, Object> updates = new HashMap<>();
        updates.put("itemName", name);
        updates.put("itemPrice", email);
        updates.put("imageUrl", imageUrl);

        usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateItemActivity.this, "Updated Profile", Toast.LENGTH_SHORT).show();
                    getUserData(itemKey);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateItemActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        imageUri = result.getData().getData();
                        itemUpdateImg.setImageURI(imageUri);
                    }
                }
            }
    );
}