package com.example.assignment2.adminPanel.classes.adminAdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteListAdapter extends RecyclerView.Adapter<DeleteListAdapter.DeleteListViewHolder>{
    private ArrayList<AddItemModel> list;
    private Context context;

    public DeleteListAdapter(ArrayList<AddItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DeleteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item,parent,false);
        return new DeleteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteListViewHolder holder, int position) {
        final AddItemModel data = list.get(position);

        Glide.with(context).load(data.getImageUrl()).placeholder(R.drawable.man).into(holder.itemImg);

        holder.itemName.setText(data.getItemName());
        holder.itemPrice.setText(data.getItemPrice());

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void removeItem(int position) {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("Inventory Management System")
                .child("Items");

        AddItemModel removedUser = list.remove(position);

        // Notify the adapter about the removal
        notifyItemRemoved(position);

        // Remove the item from Firebase
        if (removedUser != null) {
            String userId = removedUser.getItemKey();

            userRef.child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class DeleteListViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImg, deleteImg;
        TextView itemName, itemPrice;
        public DeleteListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.personImage);
            itemName = itemView.findViewById(R.id.personName);
            itemPrice = itemView.findViewById(R.id.personEmail);
            deleteImg = itemView.findViewById(R.id.deleteUser);
        }
    }
}
