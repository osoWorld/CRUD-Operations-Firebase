package com.example.assignment2.customerPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.assignment2.MainActivity;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.DeleteListAdapter;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.example.assignment2.customerPanel.classes.CustomerAdapter;
import com.example.assignment2.customerPanel.classes.CustomerModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerDashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<CustomerModel> list;
    private CustomerAdapter adapter;
    private DatabaseReference reference;
    private ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        recyclerView = findViewById(R.id.customerRecView);
        progressBar = findViewById(R.id.progressBarCustomer);
        logout = findViewById(R.id.logOutImgBack);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(CustomerDashboardActivity.this, MainActivity.class));
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        list = new ArrayList<>();

        adapter = new CustomerAdapter(list, this);

        reference = FirebaseDatabase.getInstance().getReference("Inventory Management System").child("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CustomerModel model = snapshot1.getValue(CustomerModel.class);
                    list.add(model);
                    progressBar.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}