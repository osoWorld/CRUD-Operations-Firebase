package com.example.assignment2.adminPanel.classes.adminAdapterClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.activities.adminModuleActivities.UpdateItemActivity;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;

import java.util.ArrayList;

public class UpdateListAdapter extends RecyclerView.Adapter<UpdateListAdapter.UpdateListViewHolder> {
    private ArrayList<AddItemModel> list;
    private Context context;

    public UpdateListAdapter(ArrayList<AddItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UpdateListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_layout, parent, false);
        return new UpdateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateListViewHolder holder, int position) {
        final AddItemModel data = list.get(position);

        Glide.with(context).load(data.getImageUrl()).placeholder(R.drawable.productphoto).into(holder.itemImg);

        holder.itemName.setText(data.getItemName());
        holder.itemPrice.setText(data.getItemPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UpdateItemActivity.class).putExtra("key", data.getItemKey()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UpdateListViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemName, itemPrice;

        public UpdateListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.personImage);
            itemName = itemView.findViewById(R.id.personName);
            itemPrice = itemView.findViewById(R.id.personEmail);
        }
    }
}
