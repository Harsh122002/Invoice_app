package com.example.bill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SItemDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Sale.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SItemContract.ItemEntry.TABLE_NAME + " (" +
                    SItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY," +
                    SItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER + " TEXT," +
                    SItemContract.ItemEntry.COLUMN_NAME_DATE + " TEXT," +

                    SItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME + " TEXT," +
                    SItemContract.ItemEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                    SItemContract.ItemEntry.COLUMN_NAME_PRODUCT + " TEXT," +
                    SItemContract.ItemEntry.COLUMN_NAME_QTY + " INTEGER," +
                    SItemContract.ItemEntry.COLUMN_NAME_UNIT + " TEXT," +
                    SItemContract.ItemEntry.COLUMN_NAME_PRICE + " INTEGER," + // Changed from REAL to INTEGER
                    SItemContract.ItemEntry.COLUMN_NAME_GST + " INTEGER," + // Changed from REAL to INTEGER
                    SItemContract.ItemEntry.COLUMN_NAME_DISCOUNT + " INTEGER," + // Changed from REAL to INTEGER
                    SItemContract.ItemEntry.COLUMN_NAME_AMOUNT + " INTEGER," + // Changed from REAL to INTEGER
                    SItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT + " INTEGER)"; // Changed from REAL to INTEGER


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SItemContract.ItemEntry.TABLE_NAME;

    public SItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
