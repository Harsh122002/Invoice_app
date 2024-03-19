//package com.example.bill;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    // Declare variables for EditText fields
//    EditText shopNameEditText, gstNoEditText, mobileEditText, emailEditText, addressEditText, passwordEditText, pincodeEditText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        // Initialize EditText fields
//        shopNameEditText = findViewById(R.id.shopNameEditText);
//        gstNoEditText = findViewById(R.id.gstNoEditText);
//        mobileEditText = findViewById(R.id.mobileEditText);
//        emailEditText = findViewById(R.id.emailEditText);
//        addressEditText = findViewById(R.id.addressEditText);
//        passwordEditText = findViewById(R.id.passwordEditText);
//        pincodeEditText = findViewById(R.id.pincodeEditText);
//
//        // Retrieve data from the database
//        retrieveDataFromDatabase();
//    }
//
//    private void retrieveDataFromDatabase() {
//        // Create an instance of the database helper class
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//
//        // Get the database in read mode
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        // Define a projection that specifies which columns from the database you will actually use
//        String[] projection = {
//                DatabaseContract.UserEntry.COLUMN_SHOP_NAME,
//                DatabaseContract.UserEntry.COLUMN_GST_NO,
//                DatabaseContract.UserEntry.COLUMN_MOBILE,
//                DatabaseContract.UserEntry.COLUMN_EMAIL,
//                DatabaseContract.UserEntry.COLUMN_ADDRESS,
//                DatabaseContract.UserEntry.COLUMN_PASSWORD,
//                DatabaseContract.UserEntry.COLUMN_PINCODE
//        };
//
//        // Define a selection clause
//        String selection = null;
//
//        // Define selection arguments
//        String[] selectionArgs = null;
//
//        // Define sort order
//        String sortOrder = null;
//
//        // Perform a query on the database
//        Cursor cursor = db.query(
//                DatabaseContract.UserEntry.TABLE_NAME,   // The table to query
//                projection,                             // The array of columns to return (pass null to get all)
//                selection,                              // The columns for the WHERE clause
//                selectionArgs,                          // The values for the WHERE clause
//                null,                                   // Don't group the rows
//                null,                                   // Don't filter by row groups
//                sortOrder                               // The sort order
//        );
//
//        // Iterate through the cursor and populate EditText fields with retrieved data
//        if (cursor != null && cursor.moveToFirst()) {
//            shopNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_SHOP_NAME)));
//            gstNoEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_GST_NO)));
//            mobileEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_MOBILE)));
//            emailEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL)));
//            addressEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_ADDRESS)));
//            passwordEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PASSWORD)));
//            pincodeEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PINCODE)));
//            cursor.close();
//        }
//
//        // Close the database
//        db.close();
//    }
//}
