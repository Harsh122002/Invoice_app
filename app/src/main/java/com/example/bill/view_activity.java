package com.example.bill;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class view_activity extends AppCompatActivity {

    // Declare TextView variables to hold references to the views
    private TextView companyNameTextView;
    private TextView addressTextView;
    private TextView clientNameTextView;
    private TextView clientAddressTextView;
    private TextView productNameTextView;
    private TextView qtyTextView;
    private TextView unitTextView;
    private TextView priceTextView;
    private TextView gstTextView;
    private TextView discountTextView;
    private TextView amountTextView;
    private TextView totalAmountTextView;
    private TextView company_gstTextView;

    private TextView     DateTextview;
    private TextView     InvoiceTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Find views by their IDs
        company_gstTextView=findViewById(R.id.Gst_co);
        DateTextview=findViewById(R.id.Date_p);
        InvoiceTextView=findViewById(R.id.Date_p);

        companyNameTextView = findViewById(R.id.company_name);
        addressTextView = findViewById(R.id.addressTextView);
        clientNameTextView = findViewById(R.id.clientNameTextView);
        clientAddressTextView = findViewById(R.id.clientAddressTextView);
        productNameTextView = findViewById(R.id.productNameTextView);
        qtyTextView = findViewById(R.id.qtyTextView);
        unitTextView = findViewById(R.id.unitTextView);
        priceTextView = findViewById(R.id.priceTextView);
        gstTextView = findViewById(R.id.gstTextView);
        discountTextView = findViewById(R.id.discountTextView);
        amountTextView = findViewById(R.id.amountTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);

//        Intent intent = getIntent();
//        if (intent != null) {
//            String companyName = intent.getStringExtra("shopNameEditText");
//            String address = intent.getStringExtra("addressEditText");
//            Log.d("Debug", "Company Name: " + companyName);
//            Log.d("Debug", "Address: " + address);
//            // Set the retrieved data to the TextViews
//            companyNameTextView.setText(companyName);
//            addressTextView.setText(address);
//        }
        // Retrieve the shop name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String shopName = prefs.getString("SHOP_NAME", "DefaultShopName");
        String Gst = prefs.getString("SHOP_Gst", "DefaultShopGst");
        String Address = prefs.getString("SHOP_Address", "DefaultShopaddress");


        companyNameTextView.setText(shopName);
        company_gstTextView.setText(Gst);
        addressTextView.setText(Address);


        // You can now use these TextView objects to manipulate their properties or set text, etc.
    }
}
