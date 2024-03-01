package com.example.assignment2.adminPanel.classes.adminAdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.adminPanel.classes.adminModelClass.AddItemModel;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private ArrayList<AddItemModel> list;
    private Context context;

    public ItemListAdapter(ArrayList<AddItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_layout,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        final AddItemModel data = list.get(position);

        Glide.with(context).load(data.getImageUrl()).placeholder(R.drawable.productphoto).into(holder.itemImg);

        holder.itemName.setText(data.getItemName());
        holder.itemPrice.setText(data.getItemPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemListViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImg;
        TextView itemName, itemPrice;
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.personImage);
            itemName = itemView.findViewById(R.id.personName);
            itemPrice = itemView.findViewById(R.id.personEmail);

        }
    }
}
