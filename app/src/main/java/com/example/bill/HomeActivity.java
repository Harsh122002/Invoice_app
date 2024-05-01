package com.example.bill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView saleInvoiceTextView;
    private TextView purchaseInvoiceTextView;
    private TextView paymentTextView;
    private TextView itemAddTextView;
    private TextView clientAddTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find views
        saleInvoiceTextView = findViewById(R.id.saleInvoice);
        purchaseInvoiceTextView = findViewById(R.id.purchaseInvoice);
        paymentTextView = findViewById(R.id.payment);
        itemAddTextView = findViewById(R.id.item_add);
        clientAddTextView = findViewById(R.id.client_add);

        // Set click listeners
        saleInvoiceTextView.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SaleInvoiceActivity.class)));

        purchaseInvoiceTextView.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, PurchaseInvoiceActivity.class)));

        paymentTextView.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, PaymentActivity.class)));

        itemAddTextView.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ItemAddActivity.class)));

        clientAddTextView.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ClientAddActivity.class)));

        // Set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("INVOICE APP");
        toolbar.setTitleTextColor(Color.WHITE);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

             if (itemId == R.id.menu_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                finish(); // Finish the current activity
                return true;
            }
            return false;
        });

        // Set default selected item
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
            // Handle client add item click
            handleclientAdd();
            return true;
        } else if (itemId == R.id.action_item) {
            // Handle item add item click
            handleitemAdd();
            return true;
        } else if (itemId == R.id.action_logout) {
            // Handle logout item click
            handleLogout();
            return true;
        } else {
            // If the item ID does not match any of the known IDs, call the superclass method
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleSaleInvoiceDetail() {
        // Add code to handle sale invoice detail item click
        Toast.makeText(this, "Sales Show...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SaleshowAcitvity.class));
    }

    private void handlePurchaseInvoiceDetail() {
        // Add code to handle purchase invoice detail item click
        Toast.makeText(this, "Purchase Show...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, PurchaseshowActivity.class));
    }

    private void handlePaymentDetail() {
        // Add code to handle payment detail item click
        Toast.makeText(this, "Payment Show...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, PaymentShowActivity.class));
    }

    private void handleitemAdd() {
        // Add code to handle item add item click
        Toast.makeText(this, "items Show...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ItemShowActivity.class));
    }

    private void handleclientAdd() {
        // Add code to handle client add item click
        Toast.makeText(this, "Client Show...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DataShowActivity.class));
    }

    private void handleLogout() {
        // Log that logout item is clicked
        Log.d("HomeActivity", "Logout item clicked");
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish(); // close this activity after logging out
    }
}
