package com.example.bill;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import java.util.List;

public class Pnew_activity extends AppCompatActivity {

    private ItemDbHelper itemDBHelper;
    private PItemDbHelper pitemDBHelper;
    private EditText qtyEditText, priceEditText, gstEditText, discountEditText, Unit, amountEditText;
    private TextView product, totalAmount;
    private Button saveButton, addButton;
    private ImageButton myImageButton;



    TextView productNameTextView = null;
    TextView qtyTextView = null;
    TextView unitTextView = null;
    TextView priceTextView = null;
    TextView gstTextView = null;
    TextView discountTextView = null;
    TextView amountTextView = null;
    private Context context;

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
        setContentView(R.layout.activity_pnew);

        try {
            // Initialize database helpers
            pitemDBHelper = new PItemDbHelper(this);
            itemDBHelper = new ItemDbHelper(this);

            // Find views by their IDs
            myImageButton = findViewById(R.id.myImageButton);
            qtyEditText = findViewById(R.id.Qty_item1);
            priceEditText = findViewById(R.id.Price);
            gstEditText = findViewById(R.id.Gst);
            discountEditText = findViewById(R.id.Discount);
            amountEditText = findViewById(R.id.Amount);
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
                        Toast.makeText(Pnew_activity.this, "Error occurred while retrieving items", Toast.LENGTH_SHORT).show();
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
                        String qtyString = qtyEditText.getText().toString();
                        String unit = Unit.getText().toString();
                        String priceString = priceEditText.getText().toString();
                        String gstString = gstEditText.getText().toString();
                        String discountString = discountEditText.getText().toString();
                        String amountString = amountEditText.getText().toString();

                        // Check if any of the EditText fields are empty
                        if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(qtyString) || TextUtils.isEmpty(unit) ||
                                TextUtils.isEmpty(priceString) || TextUtils.isEmpty(gstString) || TextUtils.isEmpty(discountString) ||
                                TextUtils.isEmpty(amountString)) {
                            // If any field is empty, show a toast message and return
                            Toast.makeText(Pnew_activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Convert strings to appropriate data types
                        int qtyEntered = Integer.parseInt(qtyString);

                        // Retrieve quantity from the database
                        int qtyFromDatabase = retrieveQuantity(productName);

                        // Check if entered quantity is greater than database quantity
                        if (!(qtyEntered <= qtyFromDatabase)){
                            Toast.makeText(Pnew_activity.this, "Entered quantity exceeds available stock", Toast.LENGTH_SHORT).show();
                            return;
                        }





                        // Database update successful
                        TableRow newRow = new TableRow(Pnew_activity.this);

                        // Create TextViews for each column in the row
                        productNameTextView = new TextView(Pnew_activity.this);
                        qtyTextView = new TextView(Pnew_activity.this);
                        unitTextView = new TextView(Pnew_activity.this);
                        priceTextView = new TextView(Pnew_activity.this);
                        gstTextView = new TextView(Pnew_activity.this);
                        discountTextView = new TextView(Pnew_activity.this);
                        amountTextView = new TextView(Pnew_activity.this);

                        // Set text for TextViews
                        qtyTextView.setText(qtyString);
                        priceTextView.setText(priceString);
                        gstTextView.setText(gstString);
                        discountTextView.setText(discountString);
                        amountTextView.setText(amountString);
                        unitTextView.setText(unit);
                        productNameTextView.setText(productName);
                        Button deleteButton = new Button(Pnew_activity.this);
                        deleteButton.setText("Delete");


                        // Add TextViews to the TableRow
                        newRow.addView(productNameTextView);
                        newRow.addView(qtyTextView);
                        newRow.addView(unitTextView);
                        newRow.addView(priceTextView);
                        newRow.addView(gstTextView);
                        newRow.addView(discountTextView);
                        newRow.addView(amountTextView);
                        newRow.addView(deleteButton);


                        // Add the new row to the table layout
                        TableLayout tableLayout = findViewById(R.id.tableLayout);
                        tableLayout.addView(newRow);
                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Remove the TableRow from its parent (the TableLayout)
                                tableLayout.removeView(newRow);
                            }
                        });

                        // Clear EditText fields after saving
                        clearEditTextFields();
                        updateTotalAmount();

                        Toast.makeText(Pnew_activity.this, "value add in table", Toast.LENGTH_SHORT).show();


                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(Pnew_activity.this, "Error occurred while parsing quantity or price", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Pnew_activity.this, "Error occurred while saving data", Toast.LENGTH_SHORT).show();
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

                        // Retrieve values from TextView fields
                        String productName = productNameTextView.getText().toString();
                        String qty = qtyTextView.getText().toString();
                        String unit = unitTextView.getText().toString();
                        String price = priceTextView.getText().toString();
                        String gst = gstTextView.getText().toString();
                        String discount = discountTextView.getText().toString();
                        String amount = amountTextView.getText().toString();

                        String qtyString = qtyTextView.getText().toString();
                        // Convert the string quantity to an integer
                        int qtyEntered1 = Integer.parseInt(qtyString);
                        int qtyFromDatabase = retrieveQuantity(productName);

                        // Assuming you have logic to calculate the updated quantity based on the operation you're performing
                        int updatedQty = qtyFromDatabase - qtyEntered1;

                        // Update quantity in the database
                        boolean isUpdateSuccessful = itemDBHelper.updateQuantityForProduct1(productName, updatedQty);

                        if(isUpdateSuccessful){
                            // Check if any of the TextView fields are empty
                            if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(qty) || TextUtils.isEmpty(unit) ||
                                    TextUtils.isEmpty(price) || TextUtils.isEmpty(gst) || TextUtils.isEmpty(discount) ||
                                    TextUtils.isEmpty(amount)) {
                                // If any field is empty, show a toast message and return
                                Toast.makeText(Pnew_activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Calculate total amount
                            double totalAmount = calculateTotalAmountInTable();

                            // Create a list to hold the order
                            List<Pnew_activity.Order> orders = new ArrayList<>();

                            // Create a new order object
                            Pnew_activity.Order order = new Pnew_activity.Order(productName, qty, unit, price, gst, discount, amount, totalAmount);

                            // Add the order to the list
                            orders.add(order);

                            // Log debug information for each order in the list
                            Log.d("insertOrderData", "Order: " + amount + " " + gst);

                            // Insert data into the database for the current item
                            insertOrderData(invoiceNumber, selectedDate, clientName, address, orders);

                            // Show a toast message indicating add action
                            Toast.makeText(Pnew_activity.this, "Data added for the current item", Toast.LENGTH_SHORT).show();

                            // Start the view_activity
                            Intent intent = new Intent(Pnew_activity.this, view_activity.class);
                            startActivity(intent);

                            // Finish the current activity
                            finish();}
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Pnew_activity.this, "Error occurred while adding data", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Pnew_activity.this, "Error occurred in onCreate()", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Pnew_activity.this, "Error occurred while calculating total amount", Toast.LENGTH_SHORT).show();
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
            amountEditText.setText(String.valueOf(discountedTotal));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Pnew_activity.this, "Error occurred while calculating total amount", Toast.LENGTH_SHORT).show();
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

        // Log debug information
        Log.d("insertOrderData", "Invoice Number: " + invoiceNumber);
        Log.d("insertOrderData", "Selected Date: " + selectedDate);
        Log.d("insertOrderData", "Client Name: " + clientName);
        Log.d("insertOrderData", "Address: " + address);

        for (Order order : orders) {
            Log.d("insertOrderData", "Order: " + order.toString());
        }

        SQLiteDatabase db = null;
        try {
            // Get writable database
            PItemDbHelper dbHelper = new PItemDbHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();

            // Begin a transaction
            db.beginTransaction();

            // Loop through each order and insert into the database
            for (Order order : orders) {
                // Create ContentValues object to store column-value pairs for the order
                ContentValues values = new ContentValues();
                values.put(PItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER, invoiceNumber);
                values.put(PItemContract.ItemEntry.COLUMN_NAME_DATE, selectedDate);
                values.put(PItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME, clientName);
                values.put(PItemContract.ItemEntry.COLUMN_NAME_ADDRESS, address);
                values.put(PItemContract.ItemEntry.COLUMN_NAME_PRODUCT, order.getProductName());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_QTY, order.getQty());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_UNIT, order.getUnit());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_PRICE, order.getPrice());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_GST, order.getGst());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_DISCOUNT, order.getDiscount());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_AMOUNT, order.getAmount());
                values.put(PItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT,order.getTotalAmount());

                // Insert the new row
                long newRowId = db.insert(PItemContract.ItemEntry.TABLE_NAME, null, values);

                // Check if insertion was successful
                if (newRowId != -1) {
                    Log.d("Insert", "Order inserted into the database: " + order.getProductName());
                } else {
                    Log.e("Insert", "Error occurred while inserting order into the database: " + order.getProductName());
                }
            }

            // Set transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while inserting orders into the database", Toast.LENGTH_SHORT).show();
        } finally {
            // End transaction
            if (db != null) {
                db.endTransaction();
                // Close the database connection
                db.close();
            }
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
    private int retrieveQuantity(String productName) {
        try {
            // Retrieve quantity using ItemDbHelper
            return itemDBHelper.getQuantityForProduct(productName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Pnew_activity.this, "Error occurred while retrieving quantity", Toast.LENGTH_SHORT).show();
            return 0; // or any default value
        }
    }


}

