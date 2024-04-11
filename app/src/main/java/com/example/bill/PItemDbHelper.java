package com.example.bill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PItemDbHelper extends SQLiteOpenHelper {

    // Define the database name and version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "purch.db";

    // Define SQL statement to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PItemContract.ItemEntry.TABLE_NAME + " (" +
                    PItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY," +
                    PItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_DATE + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_PRODUCT + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_QTY + " INTEGER," +
                    PItemContract.ItemEntry.COLUMN_NAME_UNIT + " TEXT," +
                    PItemContract.ItemEntry.COLUMN_NAME_PRICE + " INTEGER," +
                    PItemContract.ItemEntry.COLUMN_NAME_GST + " INTEGER," +
                    PItemContract.ItemEntry.COLUMN_NAME_DISCOUNT + " INTEGER," +
                    PItemContract.ItemEntry.COLUMN_NAME_AMOUNT + " INTEGER," +
                    PItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT + " INTEGER)";

    // Define SQL statement to delete the table if it exists
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PItemContract.ItemEntry.TABLE_NAME;

    // Constructor
    public PItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(SQL_DELETE_ENTRIES);
        // Create tables again
        onCreate(db);
    }

    // Called when the database needs to be downgraded



}
