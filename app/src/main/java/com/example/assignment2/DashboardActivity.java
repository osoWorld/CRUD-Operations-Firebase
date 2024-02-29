package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.assignment2.AdapterClasses.PersonsAdapter;
import com.example.assignment2.ModelClasses.PersonItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private PersonsAdapter adapter;
    private ArrayList<PersonItemModel> userDetailList;
    private ArrayList<PersonItemModel> filteredUserList;
    private AppCompatButton addUserBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        addUserBtn = findViewById(R.id.addUserButton);

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.clothesProductRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userDetailList = new ArrayList<>();
        filteredUserList = new ArrayList<>();
        adapter = new PersonsAdapter(filteredUserList,this);
        recyclerView.setAdapter(adapter);

        // Call a method to fetch data from Firebase and update userDetailList
        fetchDataFromFirebase();

        filteredUserList.addAll(userDetailList);
        // Set up the SearchView
        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchViewClothesProduct);
        searchView.setQueryHint("Search User");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });
    }

    private void filterUsers(String searchText) {
        filteredUserList.clear();

        if (TextUtils.isEmpty(searchText)) {
            // If search text is empty, show all items
            filteredUserList.addAll(userDetailList);
        } else {
            // Filter by user name or email (you can modify this as needed)
            for (PersonItemModel user : userDetailList) {
                if (user.getUserName().toLowerCase().contains(searchText.toLowerCase())
                        || user.getUserEmail().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredUserList.add(user);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void fetchDataFromFirebase() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDetailList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PersonItemModel userDetail = dataSnapshot.getValue(PersonItemModel.class);
                    if (userDetail != null) {
                        userDetailList.add(userDetail);
                    }
                }
                filteredUserList.addAll(userDetailList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}