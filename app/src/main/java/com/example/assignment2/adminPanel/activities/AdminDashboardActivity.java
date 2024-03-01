package com.example.assignment2.adminPanel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminAdapterClass.AdminModuleAdapter;
import com.example.assignment2.adminPanel.classes.adminModelClass.AdminModuleModelClass;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<AdminModuleModelClass> list;
    private AdminModuleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        recyclerView = findViewById(R.id.adminModuleRecView);
        list = new ArrayList<>();

        list.add(new AdminModuleModelClass("Add Item",R.drawable.page,1));
        list.add(new AdminModuleModelClass("Items List",R.drawable.file,2));
        list.add(new AdminModuleModelClass("Update Item",R.drawable.systemupdate,3));
        list.add(new AdminModuleModelClass("Delete Item",R.drawable.trash,4));
        list.add(new AdminModuleModelClass("Add User",R.drawable.addsid,5));
        list.add(new AdminModuleModelClass("User List",R.drawable.userslist,6));
        list.add(new AdminModuleModelClass("Order List",R.drawable.lists,7));
        list.add(new AdminModuleModelClass("Log out",R.drawable.log_out,8));

        adapter = new AdminModuleAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
}