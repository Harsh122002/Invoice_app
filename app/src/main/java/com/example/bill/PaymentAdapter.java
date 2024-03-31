package com.example.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<Payment> paymentList;
    private PaymentClickListener paymentClickListener;

    // Define the interface for handling delete button clicks
    public interface PaymentClickListener {
        void onDeleteClick(Payment payment);
    }

    public PaymentAdapter(List<Payment> paymentList, PaymentClickListener paymentClickListener) {
        this.paymentList = paymentList;
        this.paymentClickListener = paymentClickListener;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        final Payment payment = paymentList.get(position);
        holder.nameTextView.setText(payment.getName());
        holder.dateTextView.setText(payment.getDate());
        holder.optionsTextView.setText(payment.getOption());
        holder.numberTextView.setText(payment.getNumber());
        holder.amountTextView.setText(payment.getAmount());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentClickListener.onDeleteClick(payment);
            }
        });
    }
    public void updateData(List<Payment> paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView dateTextView;
        TextView optionsTextView;
        TextView numberTextView;
        TextView amountTextView;
        Button deleteButton;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            optionsTextView = itemView.findViewById(R.id.textViewOption);
            numberTextView = itemView.findViewById(R.id.textViewNumber);
            amountTextView = itemView.findViewById(R.id.textViewAmount);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
