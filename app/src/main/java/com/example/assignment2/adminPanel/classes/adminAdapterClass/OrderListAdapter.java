package com.example.assignment2.adminPanel.classes.adminAdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2.R;
import com.example.assignment2.customerPanel.classes.CustomerModel;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>{
    private ArrayList<CustomerModel> list;
    private Context context;

    public OrderListAdapter(ArrayList<CustomerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item,parent,false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        final CustomerModel data = list.get(position);

        Glide.with(context).load(data.getImageUrl()).placeholder(R.drawable.man).into(holder.personCard);

        holder.personName.setText(data.getItemName());
        holder.personEmail.setText(data.getItemPrice());
        holder.deleteButton.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderListViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView personEmail;
        ImageView deleteButton;
        ImageView personCard;
        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.personName);
            personEmail = itemView.findViewById(R.id.personEmail);
            deleteButton = itemView.findViewById(R.id.deleteUser);
            personCard = itemView.findViewById(R.id.personImage);

        }
    }
}
