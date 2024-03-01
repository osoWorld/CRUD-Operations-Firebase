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
import com.example.assignment2.adminPanel.classes.adminAdapterClass.ItemListAdapter;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.UpdateListAdapter;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateListActivity extends AppCompatActivity {
    RecyclerView itemListRecyclerView;
    ProgressBar itemListProgress;
    private ArrayList<AddItemModel> list;
    private UpdateListAdapter adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        itemListRecyclerView = findViewById(R.id.itemsListUpdateRecView);
        itemListProgress = findViewById(R.id.progressBarItemListUpdate);

        itemListProgress.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        adapter = new UpdateListAdapter(list, this);

        reference = FirebaseDatabase.getInstance().getReference("Inventory Management System").child("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    AddItemModel model = snapshot1.getValue(AddItemModel.class);
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
