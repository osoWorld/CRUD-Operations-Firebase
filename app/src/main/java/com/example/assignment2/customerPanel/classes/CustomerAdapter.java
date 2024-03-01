package com.example.assignment2.customerPanel.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.UpdateItemActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{
    private ArrayList<CustomerModel> list;
    private Context context;

    public CustomerAdapter(ArrayList<CustomerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item,parent,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final CustomerModel data = list.get(position);

        Glide.with(context).load(data.getImageUrl()).placeholder(R.drawable.man).into(holder.personCard);

        holder.personName.setText(data.getItemName());
        holder.personEmail.setText(data.getItemPrice());
        holder.deleteButton.setImageResource(R.drawable.shoppingcart);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setIsOrderPlaced("Yes");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Inventory Management System").child("Orders").child(data.getItemKey());

                Map<String, Object> updates = new HashMap<>();
                updates.put("itemName", data.getItemName());
                updates.put("itemPrice", data.getItemPrice());
                updates.put("imageUrl", data.getImageUrl());
                updates.put("isOrderPlaced",data.getIsOrderPlaced());

                usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView personEmail;
        ImageView deleteButton;
        ImageView personCard;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.personName);
            personEmail = itemView.findViewById(R.id.personEmail);
            deleteButton = itemView.findViewById(R.id.deleteUser);
            personCard = itemView.findViewById(R.id.personImage);
        }
    }
}
