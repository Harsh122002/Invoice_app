package com.example.bill;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemAddActivity extends AppCompatActivity {

    private EditText editTextItemName;
    private EditText editTextItemPrice;
    private EditText editTextItemDiscount;
    private EditText editTextGstTax,editTextqty;

    private ItemDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        // Initialize views
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemDiscount = findViewById(R.id.editTextItemDiscount);
        editTextGstTax = findViewById(R.id.gst_tax);
        editTextqty=findViewById(R.id.qty);
        Button buttonAddItem = findViewById(R.id.buttonAddItem);

        // Initialize database helper
        dbHelper = new ItemDbHelper(this);

        // Set click listener for add item button
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextItemName = findViewById(R.id.editTextItemName);
                editTextItemPrice = findViewById(R.id.editTextItemPrice);
                editTextItemDiscount = findViewById(R.id.editTextItemDiscount);
                editTextGstTax = findViewById(R.id.gst_tax);

                if (editTextGstTax.getText().toString().trim().isEmpty()) {
                    // Display message asking the user to fill GST tax
                    Toast.makeText(getApplicationContext(), "Please fill in GST tax.", Toast.LENGTH_SHORT).show();
                }
                else {
                addItemToDatabase();
                    Intent intent = new Intent(ItemAddActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }}
        });
    }

    public void addItemToDatabase() {
        // Get values from EditText fields
        String itemName = editTextItemName.getText().toString().trim();
        double itemPrice = Double.parseDouble(editTextItemPrice.getText().toString().trim());
        double itemDiscount = Double.parseDouble(editTextItemDiscount.getText().toString().trim());
        double itemGstTax = Double.parseDouble(editTextGstTax.getText().toString().trim());
        double itemqty = Double.parseDouble(editTextqty.getText().toString().trim());

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store item data
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_NAME_NAME, itemName);
        values.put(ItemContract.ItemEntry.COLUMN_NAME_PRICE, itemPrice);
        values.put(ItemContract.ItemEntry.COLUMN_NAME_DISCOUNT, itemDiscount);
        values.put(ItemContract.ItemEntry.COLUMN_NAME_GST_TAX, itemGstTax);
        values.put(ItemContract.ItemEntry.COLUMN_NAME_QTY,itemqty);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ItemContract.ItemEntry.TABLE_NAME, null, values);

        // Show a toast message indicating success or failure
        if (newRowId != -1) {
            // Insertion successful
            Toast.makeText(getApplicationContext(), "Item added successfully", Toast.LENGTH_SHORT).show();

            // Navigate to FrontActivity
            // Optional: Finish the current activity to prevent the user from navigating back to it
        } else {
            // Insertion failed
            Toast.makeText(getApplicationContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
        }

    }
}
