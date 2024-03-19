package com.example.bill;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewActivity extends AppCompatActivity {

    private ItemDbHelper itemDBHelper;
    private SItemDbHelper SitemDBHelper;
    private EditText qtyEditText, priceEditText, gstEditText, discountEditText, Unit;
    private TextView amountTextView, product, totalAmount;
    private Button saveButton, addButton;
    private ImageButton myImageButton;



    TextView productNameTextView = null;
    TextView qtyTextView = null;
    TextView unitTextView = null;
    TextView priceTextView = null;
    TextView gstTextView = null;
    TextView discountTextView = null;
    TextView amountTextViewNew = null;

    public class Order {
        private String productName;
        private String qty;
        private String unit;
        private String price;
        private String gst;
        private String discount;
        private String amount;
        private double totalAmount;

        // Constructor
        public Order(String productName, String qty, String unit, String price, String gst, String discount, String amount, double totalAmount) {
            this.productName = productName;
            this.qty = qty;
            this.unit = unit;
            this.price = price;
            this.gst = gst;
            this.discount = discount;
            this.amount = amount;
            this.totalAmount = totalAmount;
        }

        // Getter methods
        public String getProductName() {
            return productName;
        }

        public String getQty() {
            return qty;
        }

        public String getUnit() {
            return unit;
        }

        public String getPrice() {
            return price;
        }

        public String getGst() {
            return gst;
        }

        public String getDiscount() {
            return discount;
        }

        public String getAmount() {
            return amount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        try {
            // Initialize database helpers
            SitemDBHelper = new SItemDbHelper(this);
            itemDBHelper = new ItemDbHelper(this);

            // Find views by their IDs
            myImageButton = findViewById(R.id.myImageButton);
            qtyEditText = findViewById(R.id.Qty_item1);
            priceEditText = findViewById(R.id.Price);
            gstEditText = findViewById(R.id.Gst);
            discountEditText = findViewById(R.id.Discount);
            amountTextView = findViewById(R.id.Amount);
            saveButton = findViewById(R.id.buttonSave);
            addButton = findViewById(R.id.buttonAdd);
            Unit = findViewById(R.id.Unit);
            product = findViewById(R.id.product_add);
            totalAmount = findViewById(R.id.totalAmountTextView);

            // Set OnClickListener for the ImageButton
            myImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Retrieve item names from the database
                        List<String> itemNames = fetchItems();                        // Show the list of items in an AlertDialog
                        showItemList(itemNames);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NewActivity.this, "Error occurred while retrieving items", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Set OnClickListener for the Save button
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Retrieve values from EditText fields
                        String productName = product.getText().toString();
                        String qty = qtyEditText.getText().toString();
                        String unit = Unit.getText().toString();
                        String price = priceEditText.getText().toString();
                        String gst = gstEditText.getText().toString();
                        String discount = discountEditText.getText().toString();
                        String amount = amountTextView.getText().toString();

                        // Create a new row for the table
                        TableRow newRow = new TableRow(NewActivity.this);

                        // Create TextViews for each column in the row
                        productNameTextView = new TextView(NewActivity.this);
                        qtyTextView = new TextView(NewActivity.this);
                        unitTextView = new TextView(NewActivity.this);
                        priceTextView = new TextView(NewActivity.this);
                        gstTextView = new TextView(NewActivity.this);
                        discountTextView = new TextView(NewActivity.this);
                        amountTextViewNew = new TextView(NewActivity.this);

                        // Set text for TextViews
                        qtyTextView.setText(qty);
                        priceTextView.setText(price);
                        gstTextView.setText(gst);
                        discountTextView.setText(discount);
                        amountTextViewNew.setText(amount);
                        unitTextView.setText(unit);
                        productNameTextView.setText(productName);

                        // Add TextViews to the TableRow
                        newRow.addView(productNameTextView);
                        newRow.addView(qtyTextView);
                        newRow.addView(unitTextView);
                        newRow.addView(priceTextView);
                        newRow.addView(gstTextView);
                        newRow.addView(discountTextView);
                        newRow.addView(amountTextViewNew);

                        // Add the new row to the table layout
                        TableLayout tableLayout = findViewById(R.id.tableLayout);
                        tableLayout.addView(newRow);

                        // Clear EditText fields after saving
                        clearEditTextFields();

                        // Show a toast message indicating save action
                        Toast.makeText(NewActivity.this, "Data saved and added to table", Toast.LENGTH_SHORT).show();

                        // Update total amount after adding a new row
                        updateTotalAmount();
                    } catch (Exception e) {
                        Toast.makeText(NewActivity.this, "Error occurred while saving data", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Retrieve values from EditText fields
                        String invoiceNumber = getIntent().getStringExtra("invoice_number");
                        String selectedDate = getIntent().getStringExtra("selected_date");
                        String clientName = getIntent().getStringExtra("client_name");
                        String address = getIntent().getStringExtra("address");

                        String productName = product.getText().toString();
                        String qty = qtyEditText.getText().toString();
                        String unit = Unit.getText().toString();
                        String price = priceEditText.getText().toString();
                        String gst = gstEditText.getText().toString();
                        String discount = discountEditText.getText().toString();
                        String amount = amountTextView.getText().toString();

                        // Calculate total amount
                        double totalAmount = calculateTotalAmountInTable();

                        // Create a list to hold the order
                        List<Order> orders = new ArrayList<>();

                        // Create a new order object
                        Order order = new Order(productName, qty, unit, price, gst, discount, amount, totalAmount);

                        // Add the order to the list
                        orders.add(order);

                        // Insert data into the database for the current item
                        insertOrderData(invoiceNumber, selectedDate, clientName, address, orders);

                        // Show a toast message indicating add action
                        Toast.makeText(NewActivity.this, "Data added for the current item", Toast.LENGTH_SHORT).show();

                        // Start the FrontActivity
                        Intent intent = new Intent(NewActivity.this, view_activity.class);
                        startActivity(intent);

                        // Finish the current activity
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NewActivity.this, "Error occurred while adding data", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Add TextChangedListeners to all input fields
            qtyEditText.addTextChangedListener(textWatcher);
            priceEditText.addTextChangedListener(textWatcher);
            gstEditText.addTextChangedListener(textWatcher);
            discountEditText.addTextChangedListener(textWatcher);

            // Call updateTotalAmount() to initialize total amount when activity starts
            updateTotalAmount();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(NewActivity.this, "Error occurred in onCreate()", Toast.LENGTH_SHORT).show();
        }
    }

    // TextWatcher to calculate the total amount when any input field changes
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            try {
                calculateTotalAmount();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NewActivity.this, "Error occurred while calculating total amount", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Method to calculate the total amount
    private void calculateTotalAmount() {
        try {
            // Get values from input fields
            double qty = parseDouble(qtyEditText.getText().toString());
            double price = parseDouble(priceEditText.getText().toString());
            double gst = parseDouble(gstEditText.getText().toString());
            double discount = parseDouble(discountEditText.getText().toString());

            // Calculate subtotal
            double subtotal = qty * price;

            // Calculate total with GST
            double totalWithGst = subtotal + (subtotal * (gst / 100));

            // Apply discount
            double discountedTotal = totalWithGst - (totalWithGst * (discount / 100));
            discountedTotal = Math.round(discountedTotal);
            // Set calculated total amount to TextView
            amountTextView.setText(String.valueOf(discountedTotal));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(NewActivity.this, "Error occurred while calculating total amount", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to parse double from String, returns 0.0 if parsing fails
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Method to fetch item names from the database

    private List<String> fetchItems() {
        try {
            List<String> itemNames = new ArrayList<>();

            // Get readable database
            SQLiteDatabase db = itemDBHelper.getReadableDatabase();

            // Query to retrieve item names from the database
            Cursor cursor = db.rawQuery("SELECT name FROM items", null);

            // Iterate through the cursor to fetch item names
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String itemName = cursor.getString(cursor.getColumnIndex("name"));

                    itemNames.add(itemName);

                } while (cursor.moveToNext());

                // Close the cursor after use
                cursor.close();
            }

            // Close the database connection
            db.close();

            return itemNames;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while fetching items", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }


    // Method to show the list of item names in an AlertDialog
    private void showItemList(List<String> itemNames) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select an Item")
                    .setItems(itemNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle item selection here if needed
                            String selectedItem = itemNames.get(which);
                            // Perform any action with the selected item
                            // For example, you can set the selected item in a TextView
                            product.setText(selectedItem);
                        }
                    });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while showing item list", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to clear all EditText fields
    private void clearEditTextFields() {
        qtyEditText.setText("");
        priceEditText.setText("");
        gstEditText.setText("");
        discountEditText.setText("");
        Unit.setText("");
        product.setText("");
    }


        // Constructor, getters, and setters
        // Constructors, getters, and setters for the class properties

    // Method to insert data into the database
    // Method to insert data into the database
    // Method to insert order data into the database
    private void insertOrderData(String invoiceNumber, String selectedDate, String clientName, String address, List<Order> orders) {
        try {
            // Get writable database
            SQLiteDatabase db = SitemDBHelper.getWritableDatabase();

            // Loop through each order and insert into the database
            for (Order order : orders) {
                // Create ContentValues object to store column-value pairs for the order
                ContentValues values = new ContentValues();
                values.put(SItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER, invoiceNumber);
                values.put(SItemContract.ItemEntry.COLUMN_NAME_DATE, selectedDate);
                values.put(SItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME, clientName);
                values.put(SItemContract.ItemEntry.COLUMN_NAME_ADDRESS, address);
                values.put(SItemContract.ItemEntry.COLUMN_NAME_PRODUCT, order.getProductName());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_QTY, order.getQty());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_UNIT, order.getUnit());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_PRICE, order.getPrice());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_GST, order.getGst());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_DISCOUNT, order.getDiscount());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_AMOUNT, order.getAmount());
                values.put(SItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT, order.getTotalAmount());

                // Insert the new row
                long newRowId = db.insert(SItemContract.ItemEntry.TABLE_NAME, null, values);

                // Check if insertion was successful
                if (newRowId != -1) {
                    Log.d("Insert", "Order inserted into the database: " + order.getProductName());
                } else {
                    Log.e("Insert", "Error occurred while inserting order into the database: " + order.getProductName());
                }
            }

            // Close the database connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while inserting orders into the database", Toast.LENGTH_SHORT).show();
        }
    }



    // Method to update the total amount
// Method to update the total amount
    private void updateTotalAmount() {
        try {
            double totalAmountValue = calculateTotalAmountInTable();

            // Update total amount TextView
            totalAmount.setText("Total Amount: " + totalAmountValue);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while updating total amount", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to calculate total amount from the table
    private double calculateTotalAmountInTable() {
        double totalAmount = 0.0;

        // Iterate through all rows in the table layout
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        for (int i = 1; i < tableLayout.getChildCount(); i++) { // Start from index 1 to skip header row
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            // Retrieve amount TextView from the row
            TextView amountTextView = (TextView) row.getChildAt(6); // Assuming Amount is the seventh column

            // Parse amount value and add it to totalAmount
            double amount = parseDouble(amountTextView.getText().toString());
            totalAmount += amount;
        }

        return totalAmount;
    }
}
