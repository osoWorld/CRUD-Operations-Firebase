package com.example.assignment2.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.ModelClasses.PersonItemModel;
import com.example.assignment2.ModelClasses.RegisterUser;
import com.example.assignment2.R;
import com.example.assignment2.UpdateProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.PersonsViewHolder> {
    private ArrayList<PersonItemModel> userDetailList;
    private Context context;

    public PersonsAdapter(ArrayList<PersonItemModel> userDetailList, Context context) {
        this.userDetailList = userDetailList;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false);
        return new PersonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsViewHolder holder, int position) {
        PersonItemModel userDetail = userDetailList.get(position);
        holder.personName.setText(userDetail.getUserName());
        holder.personEmail.setText(userDetail.getUserEmail());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

        holder.personCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = userDetail.getUserId();
                Intent intent = new Intent(context, UpdateProfileActivity.class);
                intent.putExtra("UserId", uid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDetailList.size();
    }

    private void removeItem(int position) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        PersonItemModel removedUser = userDetailList.remove(position);

        // Notify the adapter about the removal
        notifyItemRemoved(position);

        // Remove the item from Firebase
        if (removedUser != null) {
            String userId = removedUser.getUserId();

            userRef.child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "User Removed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    static class PersonsViewHolder extends RecyclerView.ViewHolder {
        TextView personName;
        TextView personEmail;
        ImageView deleteButton;
        CardView personCard;

        public PersonsViewHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            personEmail = itemView.findViewById(R.id.personEmail);
            deleteButton = itemView.findViewById(R.id.deleteUser);
            personCard = itemView.findViewById(R.id.personCard);
        }
    }
}
