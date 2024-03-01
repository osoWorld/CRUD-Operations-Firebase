package com.example.assignment2.adminPanel.activities.adminModuleActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.DeleteListAdapter;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.OrderListAdapter;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.UpdateListAdapter;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.example.assignment2.customerPanel.classes.CustomerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    RecyclerView itemListRecyclerView;
    ProgressBar itemListProgress;
    private ArrayList<CustomerModel> list;
    private OrderListAdapter adapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        itemListProgress = findViewById(R.id.progressBarOrder);
        itemListRecyclerView = findViewById(R.id.orderListRecView);

        itemListProgress.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        adapter = new OrderListAdapter(list, this);

        reference = FirebaseDatabase.getInstance().getReference("Inventory Management System").child("Orders");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    CustomerModel model = snapshot1.getValue(CustomerModel.class);
                    list.add(model);
                    itemListProgress.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        itemListRecyclerView.setAdapter(adapter);
        itemListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}