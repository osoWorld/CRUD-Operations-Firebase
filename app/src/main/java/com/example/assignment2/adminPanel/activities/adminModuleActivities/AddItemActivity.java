package com.example.assignment2.adminPanel.activities.adminModuleActivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddItemActivity extends AppCompatActivity {
    EditText itemName, itemPrice;
    ConstraintLayout clickItemImg;
    ImageView itemImage;
    AppCompatButton addItemButton;
    private Uri imageUri;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String profileLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        itemName = findViewById(R.id.addItemName);
        itemPrice = findViewById(R.id.addItemPrice);
        clickItemImg = findViewById(R.id.addImageConstraint);
        itemImage = findViewById(R.id.addItemImage);
        addItemButton = findViewById(R.id.addItemButton);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        clickItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                imagePickerActivityResult.launch(galleryIntent);
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameString = itemName.getText().toString().trim();
                String itemPriceString = itemPrice.getText().toString().trim();

                if (itemNameString.isEmpty() || itemPriceString.isEmpty()){
                    Toast.makeText(AddItemActivity.this, "Field's cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage(itemNameString,itemPriceString,imageUri);
                }
            }
        });

    }
    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        imageUri = result.getData().getData();
                        itemImage.setImageURI(imageUri);
                    }
                }
            }
    );

    private void uploadImage(String itemName, String itemPrice, Uri imagePathUri) {
        ProgressDialog pd = new ProgressDialog(AddItemActivity.this);
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
                        addToDatabase(itemName,itemPrice,profileLink);
                        pd.dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    });
                } else {
                    Toast.makeText(AddItemActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }

    private void addToDatabase(String itemName, String itemPrice, String imageUrl) {
        DatabaseReference myRef = database.getReference("Inventory Management System").child("Items");
        String pushKey = myRef.push().getKey();
        AddItemModel obj = new AddItemModel(itemName,itemPrice,imageUrl,pushKey);
        myRef.child(pushKey).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddItemActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}