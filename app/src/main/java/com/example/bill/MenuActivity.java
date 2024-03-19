//package com.example.bill;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MenuActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home2);
//
//        // Your initialization code here
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        // Handle menu item clicks
//        int itemId = item.getItemId();
//        Log.d("MenuItemClicked", "Item ID: " + itemId); // Log the clicked item ID
//
//        // Perform action based on the clicked item ID
//        if (itemId == R.id.action_sale_invoice_detial) {
//            // Handle sale invoice detail item click
//            handleSaleInvoiceDetail();
//            return true;
//        } else if (itemId == R.id.action_purchase_invoice_detial) {
//            // Handle purchase invoice detail item click
//            handlePurchaseInvoiceDetail();
//            return true;
//        } else if (itemId == R.id.action_payment_detial) {
//            // Handle payment detail item click
//            handlePaymentDetail();
//            return true;
//        } else if (itemId == R.id.action_item) {
//            // Handle item add item click
//            handleItemAdd();
//            return true;
//        } else if (itemId == R.id.action_logout) {
//            // Handle logout item click
//            handleLogout();
//            return true;
//        } else {
//            // If the item ID does not match any of the known IDs, call the superclass method
//            return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void handleSaleInvoiceDetail() {
//        // Add code to handle sale invoice detail item click
//    }
//
//    private void handlePurchaseInvoiceDetail() {
//        // Add code to handle purchase invoice detail item click
//    }
//
//    private void handlePaymentDetail() {
//        // Add code to handle payment detail item click
//    }
//
//    private void handleItemAdd() {
//        // Add code to handle item add item click
//    }
//
//    private void handleLogout() {
//        // Log that logout item is clicked
//        Log.d("MenuActivity", "Logout item clicked");
//
//        // Start MainActivity to perform logout
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
//}
