package com.example.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.SalesViewHolder> {

    private final SaleshowAcitvity SaleClickListener  ;
    private List<Sales> salesList;
    private SaleClickListener itemClickListener;

    public interface SaleClickListener {


        void onDeleteClick(Sales sales);
    }


    // Define the interface for handling delete button clicks


    public SaleAdapter(List<Sales> salesList, SaleshowAcitvity SaleClickListener) {
        this.salesList = salesList;
        this.SaleClickListener = SaleClickListener;
    }


    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        final Sales sales = salesList.get(position);
        holder.textViewNumber.setText(sales.getInvoiceNumber());
        holder.textViewDate.setText(sales.getDate());
        holder.textViewName.setText(sales.getClientName());
        holder.textViewAddress.setText(sales.getAddress());
        holder.textViewProduct.setText(sales.getProduct());
        holder.textViewQty.setText(sales.getQuantity());
        holder.textViewUnit.setText(sales.getUnit());
        holder.textViewPrice.setText(sales.getPrice());
        holder.textViewGst.setText(sales.getGst());
        holder.textViewDiscount.setText(sales.getDiscount());
        holder.textViewAmount.setText(sales.getAmount());
        holder.textViewAllAmount.setText(sales.getTotalAmount());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleClickListener.onDeleteClick(sales);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public void updateData(List<Sales> sales) {
        salesList.clear();
        salesList.addAll(sales);
        notifyDataSetChanged();
    }



    static class SalesViewHolder extends RecyclerView.ViewHolder {
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

        public SalesViewHolder(@NonNull View itemView) {
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
