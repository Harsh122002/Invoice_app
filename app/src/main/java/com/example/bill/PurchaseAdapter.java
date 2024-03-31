package com.example.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> purchaseList;
    private PurchaseClickListener clickListener;

    public interface PurchaseClickListener {
        void onDeleteClick(Purchase purchase);
    }

    public PurchaseAdapter(List<Purchase> purchaseList, PurchaseClickListener clickListener) {
        this.purchaseList = purchaseList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        final Purchase purchase = purchaseList.get(position);
        holder.textViewNumber.setText(purchase.getInvoiceNumber());
        holder.textViewDate.setText(purchase.getDate());
        holder.textViewName.setText(purchase.getClientName());
        holder.textViewAddress.setText(purchase.getAddress());
        holder.textViewProduct.setText(purchase.getProduct());
        holder.textViewQty.setText(purchase.getQuantity());
        holder.textViewUnit.setText(purchase.getUnit());
        holder.textViewPrice.setText(purchase.getPrice());
        holder.textViewGst.setText(purchase.getGst());
        holder.textViewDiscount.setText(purchase.getDiscount());
        holder.textViewAmount.setText(purchase.getAmount());
        holder.textViewAllAmount.setText(purchase.getTotalAmount());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onDeleteClick(purchase);
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public void updateData(List<Purchase> purchases) {
        purchaseList.clear();
        purchaseList.addAll(purchases);
        notifyDataSetChanged();
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber;
        TextView textViewDate;
        TextView textViewName;
        TextView textViewAddress;
        TextView textViewProduct;
        TextView textViewQty;
        TextView textViewUnit;
        TextView textViewPrice;
        TextView textViewGst;
        TextView textViewDiscount;
        TextView textViewAmount;
        TextView textViewAllAmount;
        Button deleteButton;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewProduct = itemView.findViewById(R.id.textViewProduct);
            textViewQty = itemView.findViewById(R.id.textViewQty);
            textViewUnit = itemView.findViewById(R.id.textViewUnit);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewGst = itemView.findViewById(R.id.textViewGst);
            textViewDiscount = itemView.findViewById(R.id.textViewDiscount);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewAllAmount = itemView.findViewById(R.id.textViewAllAmount);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
