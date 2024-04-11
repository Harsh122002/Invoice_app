package com.example.bill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Items.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " (" +
                    ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY," +
                    ItemContract.ItemEntry.COLUMN_NAME_NAME + " TEXT," +
                    ItemContract.ItemEntry.COLUMN_NAME_PRICE + " REAL," +
                    ItemContract.ItemEntry.COLUMN_NAME_DISCOUNT + " REAL," +
                    ItemContract.ItemEntry.COLUMN_NAME_GST_TAX + " REAL," +
                    ItemContract.ItemEntry.COLUMN_NAME_QTY + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE_NAME;
    private static final String SQL_UPDATE_QUANTITY =
            "UPDATE " + ItemContract.ItemEntry.TABLE_NAME +
                    " SET " + ItemContract.ItemEntry.COLUMN_NAME_QTY + " = ?" +
                    " WHERE " + ItemContract.ItemEntry.COLUMN_NAME_NAME + " = ?";

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public int getQuantityForProduct(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int quantity = 0;

        try {
            // Query to retrieve quantity for the given product name
            Cursor cursor = db.rawQuery("SELECT qty FROM items WHERE name = ?", new String[]{productName});

            // Check if cursor is not null and move to first row
            if (cursor != null && cursor.moveToFirst()) {
                // Retrieve quantity from cursor
                quantity = cursor.getInt(cursor.getColumnIndex("qty"));
            }

            // Close cursor after use
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions if any
        } finally {
            // Close database connection
            db.close();
        }

        return quantity;
    }

    public void updateQuantityForProduct(String productName, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Execute update query
            db.execSQL(SQL_UPDATE_QUANTITY, new Object[]{newQuantity, productName});
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        } finally {
            // Close database connection
            db.close();
        }
    }
    public boolean updateQuantityForProduct1(String productName, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_NAME_QTY, newQuantity);

        try {
            // Update the quantity for the given product name
            int rowsAffected = db.update(
                    ItemContract.ItemEntry.TABLE_NAME,
                    values,
                    ItemContract.ItemEntry.COLUMN_NAME_NAME + " = ?",
                    new String[]{productName}
            );

            return rowsAffected > 0; // Return true if rows were affected (update successful)
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            return false; // Return false if update failed
        } finally {
            // Close database connection
            db.close();
        }
    }}
