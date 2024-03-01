package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment2.ModelClasses.PersonItemModel;
import com.example.assignment2.ModelClasses.RegisterUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText name, email, password;
    private AppCompatButton updateButton, homeButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        ids();

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");

        getUserData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString().trim();
                String emails = email.getText().toString().trim();
                String passwords = password.getText().toString().trim();

                updateProfile(names,emails,passwords);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdateProfileActivity.this,DashboardActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void ids() {
        name = findViewById(R.id.nameUpdate);
        email = findViewById(R.id.emailUpdate);
        password = findViewById(R.id.passwordUpdate);
        updateButton = findViewById(R.id.updateButton);
        homeButton = findViewById(R.id.backButton);
    }

    private void getUserData() {
        if (userId != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        PersonItemModel userDetail = snapshot.getValue(PersonItemModel.class);

                        // Check if the userDetail is not null
                        if (userDetail != null) {
                            // Update UI with user details

                            String names = userDetail.getUserName();
                            String emails = userDetail.getUserEmail();
                            String passwords = userDetail.getUserPassword();
                            name.setText(names);
                            email.setText(emails);
                            password.setText(passwords);

                            Toast.makeText(UpdateProfileActivity.this, "User Found", Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle the case where userDetail is null
                            Toast.makeText(UpdateProfileActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle the case where userId does not exist in the database
                        Toast.makeText(UpdateProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UpdateProfileActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void updateProfile(String name, String email, String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users").child(userId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("userName",name);
        updates.put("userEmail",email);
        updates.put("userPassword",password);

        usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateProfileActivity.this, "Updated Profile", Toast.LENGTH_SHORT).show();
                    getUserData();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfileActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}