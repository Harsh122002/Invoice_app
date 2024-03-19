package com.example.bill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

        // Database version
        private static final int DATABASE_VERSION = 1;

        // Database name
        private static final String DATABASE_NAME = "BillDatabase.db";

        // Table name and columns for Bill Database
        public static final String USER_TABLE_NAME = "User";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SHOP_NAME = "shop_name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PIN_CODE = "pin_code";
        public static final String COLUMN_GST_NUMBER = "gst_number";

        // SQL statement to create user table for Bill Database
        public static final String CREATE_USER_TABLE =
                "CREATE TABLE " + USER_TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SHOP_NAME + " TEXT, " +
                        COLUMN_PHONE + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_ADDRESS + " TEXT, " +
                        COLUMN_PIN_CODE + " TEXT, " +
                        COLUMN_GST_NUMBER + " TEXT" +
                        ")";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create User table for Bill Database
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop the existing table if it exists and create a new one when upgrading the database
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
        }

        // Method to retrieve user by email and password
        public Cursor getUserByEmailAndPassword(String enteredEmail, String enteredPassword) {
            SQLiteDatabase db = this.getReadableDatabase();

            // Define the columns to retrieve
            String[] projection = {COLUMN_EMAIL, COLUMN_PASSWORD};

            // Define the selection criteria
            String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";

            // Define the selection arguments
            String[] selectionArgs = {enteredEmail, enteredPassword};

            // Query the database
            Cursor cursor = db.query(
                    USER_TABLE_NAME,  // The table name
                    projection,      // The columns to return
                    selection,       // The columns for the WHERE clause
                    selectionArgs,   // The values for the WHERE clause
                    null,            // don't group the rows
                    null,            // don't filter by row groups
                    null             // The sort order
            );

            return cursor;
        }

        // Method to check user credentials for the original User table
        public boolean checkUserCredentials(String email, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(USER_TABLE_NAME, null, COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?",
                    new String[]{email, password}, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }
    public ArrayList<DatabaseContract> getLogged(String enteredEmail) {
        ArrayList<DatabaseContract> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{enteredEmail});

        // Check if the cursor is not null and has some data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a DatabaseContract object
                DatabaseContract user = new DatabaseContract();

                // Populate the object with data from the cursor

                user.setShopName(cursor.getString(cursor.getColumnIndex(COLUMN_SHOP_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                user.setPinCode(cursor.getString(cursor.getColumnIndex(COLUMN_PIN_CODE)));
                user.setGstNumber(cursor.getString(cursor.getColumnIndex(COLUMN_GST_NUMBER)));

                // Add the user object to the ArrayList
                userList.add(user);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        sqLiteDatabase.close();

        return userList;
    }
    public boolean updateUserDetails(String enteredEmail, String shopName, String mobile, String email, String address, String password, String pincode, String gstNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHOP_NAME, shopName);
        values.put(COLUMN_PHONE, mobile);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PIN_CODE, pincode);
        values.put(COLUMN_GST_NUMBER, gstNo);

        // Updating row
        int rowsAffected = db.update(USER_TABLE_NAME, values,
                COLUMN_EMAIL + " = ?",
                new String[]{enteredEmail});

        return rowsAffected > 0;
    }


}


