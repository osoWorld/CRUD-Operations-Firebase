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
import com.example.assignment2.adminPanel.classes.adminAdapterClass.UpdateListAdapter;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteItemActivity extends AppCompatActivity {
    private RecyclerView deleteRecyclerView;
    private ProgressBar deleteProgressBar;
    private ArrayList<AddItemModel> list;
    private DatabaseReference reference;
    private DeleteListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        deleteRecyclerView = findViewById(R.id.deleteItemListRecView);
        deleteProgressBar = findViewById(R.id.progressBarItemListDelete);

        deleteProgressBar.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        adapter = new DeleteListAdapter(list, this);

        reference = FirebaseDatabase.getInstance().getReference("Inventory Management System").child("Items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    AddItemModel model = snapshot1.getValue(AddItemModel.class);
                    list.add(model);
                    deleteProgressBar.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        deleteRecyclerView.setAdapter(adapter);
        deleteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}