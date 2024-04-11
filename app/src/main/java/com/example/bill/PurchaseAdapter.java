package com.example.bill;

import android.content.Context;
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
    private Context context; // Ensure you initialize this context in your constructor if needed.

    public interface PurchaseClickListener {
        void onDeleteClick(Purchase purchase);
    }

    // Modified constructor to accept Context as well.
    public PurchaseAdapter(List<Purchase> purchaseList, PurchaseClickListener clickListener) {
        this.context = context;
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
        holder.textViewQty.setText(String.valueOf(purchase.getQuantity())); // Make sure to convert numerical values to String
        holder.textViewUnit.setText(purchase.getUnit());
        holder.textViewPrice.setText(String.valueOf(purchase.getPrice()));
        holder.textViewGst.setText(String.valueOf(purchase.getGst()));
        holder.textViewDiscount.setText(String.valueOf(purchase.getDiscount()));
        holder.textViewAmount.setText(String.valueOf(purchase.getAmount()));
        holder.textViewAllAmount.setText(String.valueOf(purchase.getTotalAmount()));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Invoke the onDeleteClick method
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

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewDate, textViewName, textViewAddress, textViewProduct, textViewQty, textViewUnit, textViewPrice, textViewGst, textViewDiscount, textViewAmount, textViewAllAmount;
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
