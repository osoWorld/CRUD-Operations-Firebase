package com.example.assignment2.adminPanel.activities.adminModuleActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.assignment2.AdapterClasses.PersonsAdapter;
import com.example.assignment2.DashboardActivity;
import com.example.assignment2.ModelClasses.PersonItemModel;
import com.example.assignment2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView usersRecyclerView;
    private ProgressBar usersProgress;
    private PersonsAdapter adapter;
    private ArrayList<PersonItemModel> userDetailList;
    private ArrayList<PersonItemModel> filteredUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_bg));
        }

        usersRecyclerView = findViewById(R.id.itemsListUsersRecView);
        usersProgress = findViewById(R.id.progressBarItemListUsers);

        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userDetailList = new ArrayList<>();
        filteredUserList = new ArrayList<>();

        adapter = new PersonsAdapter(filteredUserList,this);
        usersRecyclerView.setAdapter(adapter);

        // Call a method to fetch data from Firebase and update userDetailList
        fetchDataFromFirebase();

        filteredUserList.addAll(userDetailList);
        // Set up the SearchView
        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchViewUsersList);
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
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Profiles");

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
                Toast.makeText(UserListActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}