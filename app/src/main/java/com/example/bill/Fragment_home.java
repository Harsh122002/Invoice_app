package com.example.bill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;



public class Fragment_home extends Fragment {

    private TextView saleInvoiceTextView;
    private TextView purchaseInvoiceTextView;
    private TextView paymentTextView;
    private TextView itemAddTextView;
    private TextView clientAddTextView;

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true); // Ensure onCreateOptionsMenu() is called
//    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);


        // Find views
        saleInvoiceTextView = view.findViewById(R.id.saleInvoice);
        purchaseInvoiceTextView = view.findViewById(R.id.purchaseInvoice);
        paymentTextView = view.findViewById(R.id.payment);
        itemAddTextView = view.findViewById(R.id.item_add);
        clientAddTextView = view.findViewById(R.id.client_add);

        // Set click listeners
        saleInvoiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SaleInvoiceActivity.class));
            }
        });

        purchaseInvoiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PurchaseInvoiceActivity.class));
            }
        });

        paymentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentActivity.class));
            }
        });

        itemAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ItemAddActivity.class));
            }
        });

        clientAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClientAddActivity.class));
            }
        });

        // Set toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Bill Application");

        setHasOptionsMenu(true); // Enable options menu





        return view;
    }

    @Override

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        int itemId = item.getItemId();


        Log.d("MenuItemClicked", "Item ID: " + itemId); // Log the clicked item ID

        // Perform action based on the clicked item ID
        if (itemId == R.id.action_sale_invoice_detial) {
            // Handle sale invoice detail item click
            handleSaleInvoiceDetail();
            return true;
        } else if (itemId == R.id.action_purchase_invoice_detial) {
            // Handle purchase invoice detail item click
            handlePurchaseInvoiceDetail();
            return true;
        } else if (itemId == R.id.action_payment_detial) {
            // Handle payment detail item click
            handlePaymentDetail();
            return true;
        } else if (itemId == R.id.action_client) {
            // Handle item add item click
            handleclientAdd();
            return true;
        } else if (itemId == R.id.action_item) {
            // Handle item add item click
            handleitemAdd();
        } else if (itemId == R.id.action_logout) {
            // Handle logout item click
            handleLogout();
            return true;
        } else {
            // If the item ID does not match any of the known IDs, call the superclass method
            return super.onOptionsItemSelected(item);
        }
    return false;
    }

    private void handleSaleInvoiceDetail() {
        // Add code to handle sale invoice detail item click
        Toast.makeText(getActivity(), "Sales Show...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), SaleshowAcitvity.class);
        startActivity(intent);
    }

    private void handlePurchaseInvoiceDetail() {
        // Add code to handle purchase invoice detail item click
        Toast.makeText(getActivity(), "Purchase Show...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), PurchaseshowActivity.class);
        startActivity(intent);

    }

    private void handlePaymentDetail() {
        Toast.makeText(getActivity(), "Payment Show...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), PaymentShowActivity.class);
        startActivity(intent);
        // Add code to handle paym
        // Add code to handle payment detail item click
    }

    private void handleitemAdd() {
        Toast.makeText(getActivity(), "items Show...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), ItemShowActivity.class);
        startActivity(intent);
        // Add code to handle payment detail item click
    }

    private void handleclientAdd() {
        Toast.makeText(getActivity(), "Client Show...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), DataShowActivity.class);
        startActivity(intent);
        // Add code to handle item add item click
    }

    private void handleLogout() {
        // Log that logout item is clicked
        Log.d("Fragment_home", "Logout item clicked");
        Toast.makeText(getActivity(), "Logging out...", Toast.LENGTH_SHORT).show();

        // Start MainActivity to perform logout
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }


}

