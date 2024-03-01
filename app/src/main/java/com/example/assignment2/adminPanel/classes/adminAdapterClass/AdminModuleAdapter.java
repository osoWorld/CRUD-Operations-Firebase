package com.example.assignment2.adminPanel.classes.adminAdapterClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.MainActivity;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.AddItemActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.DeleteItemActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.ItemListActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.OrderListActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.UpdateItemActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.UpdateListActivity;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.UserListActivity;
import com.example.assignment2.adminPanel.classes.adminModelClass.AdminModuleModelClass;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminModuleAdapter extends RecyclerView.Adapter<AdminModuleAdapter.AdminViewHolder>{
    private ArrayList<AdminModuleModelClass> list;
    private Context context;

    public AdminModuleAdapter(ArrayList<AdminModuleModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_module_layout,parent,false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        final AdminModuleModelClass data = list.get(position);

        holder.adminItemImg.setImageResource(data.getModuleImg());
        holder.adminItemName.setText(data.getModuleName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = data.getModulePosition();

                if (id == 1){
                    context.startActivity(new Intent(context, AddItemActivity.class));
                } else if (id == 2) {
                    context.startActivity(new Intent(context, ItemListActivity.class));
                } else if (id == 3) {
                    context.startActivity(new Intent(context, UpdateListActivity.class));
                } else if (id == 4) {
                    context.startActivity(new Intent(context, DeleteItemActivity.class));
                } else if (id == 5) {
                    context.startActivity(new Intent(context, MainActivity.class));
                } else if (id == 6) {
                    context.startActivity(new Intent(context, UserListActivity.class));
                } else if (id == 7) {
                    context.startActivity(new Intent(context, OrderListActivity.class));
                } else if (id == 8) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Toast.makeText(context, "Invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder{
        ImageView adminItemImg;
        TextView adminItemName;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            adminItemImg = itemView.findViewById(R.id.adminDashImg);
            adminItemName = itemView.findViewById(R.id.adminDashText);

        }
    }
}
