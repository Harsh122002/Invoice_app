package com.example.bill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    // Database and table constants
    private static final String DATABASE_NAME = "Client_Detail.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ClientContract.ClientEntry.TABLE_NAME + " (" +
                    ClientContract.ClientEntry._ID + " INTEGER PRIMARY KEY," +
                    ClientContract.ClientEntry.COLUMN_NAME_NAME + " TEXT," +
                    ClientContract.ClientEntry.COLUMN_NAME_ROLE + " TEXT," +
                    ClientContract.ClientEntry.COLUMN_NAME_GST_NUMBER + " TEXT," +
                    ClientContract.ClientEntry.COLUMN_NAME_MOBILE_NUMBER + " TEXT," +
                    ClientContract.ClientEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    ClientContract.ClientEntry.COLUMN_NAME_ADDRESS + " TEXT)";

    // SQL statement to drop the table if exists
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ClientContract.ClientEntry.TABLE_NAME;

    public ClientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }



}
