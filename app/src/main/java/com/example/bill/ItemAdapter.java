package com.example.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewHolder> {

    private List<Items> itemsList;
    private ItemClickListener itemClickListener;

    // Define the interface for handling delete button clicks
    public interface ItemClickListener {
        void onDeleteClick(Items item);
    }

    public ItemAdapter(List<Items> itemsList, ItemClickListener itemClickListener) {
        this.itemsList = itemsList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        final Items item = itemsList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(item.getPrice());
        holder.discountTextView.setText(item.getDiscount());
        holder.gstTextView.setText(item.getGst());
        holder.qtyTextView.setText(item.getqty());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onDeleteClick(item);

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void updateData(List<Items> items) {
        itemsList.clear();
        itemsList.addAll(items);
        notifyDataSetChanged();
    }

    static class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView discountTextView;
        TextView gstTextView,qtyTextView;

        Button deleteButton;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            discountTextView = itemView.findViewById(R.id.discountTextView);
            gstTextView = itemView.findViewById(R.id.gstTextView);
            qtyTextView = itemView.findViewById(R.id.viewqty);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
