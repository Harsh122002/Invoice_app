package com.example.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> clientList;
    private ClientClickListener clientClickListener;

    // Define the interface for handling delete button clicks
    public interface ClientClickListener {
        void onDeleteClick(Client client);
    }

    public ClientAdapter(List<Client> clientList, ClientClickListener clientClickListener) {
        this.clientList = clientList;
        this.clientClickListener = clientClickListener;
    }




    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        final Client client = clientList.get(position);
        holder.nameTextView.setText(client.getName());
        holder.roleTextView.setText(client.getRole());
        holder.gstNumberTextView.setText(client.getGstNumber());
        holder.mobileNumberTextView.setText(client.getMobileNumber());
        holder.emailTextView.setText(client.getEmail());
        holder.addressTextView.setText(client.getAddress());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientClickListener.onDeleteClick(client);
            }
        });
    }


    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void updateData(List<Client> clients) {
        clientList.clear();
        clientList.addAll(clients);
        notifyDataSetChanged();
    }

    class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView roleTextView;
        TextView gstNumberTextView;
        TextView mobileNumberTextView;
        TextView emailTextView;
        TextView addressTextView;

        public Button deleteButton;


        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
            gstNumberTextView = itemView.findViewById(R.id.gstNumberTextView);
            mobileNumberTextView = itemView.findViewById(R.id.mobileNumberTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Client client) {
            nameTextView.setText(client.getName());
            roleTextView.setText(client.getRole());
            gstNumberTextView.setText(client.getGstNumber());
            mobileNumberTextView.setText(client.getMobileNumber());
            emailTextView.setText(client.getEmail());
            addressTextView.setText(client.getAddress());
        }
    }
}

