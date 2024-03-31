package com.example.bill;


import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PDatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "payment.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_PAYMENT = "payment";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_OPTION = "option";
    public static final String COLUMN_NAME="nameTEXT";
    public static final String COLUMN_NUMBER="numberTEXT";
    public static final String COLUMN_AMOUNT="amount";


    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_PAYMENT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_NUMBER+"TEXT,"+
                    COLUMN_NAME +"TEXT,"+
                    COLUMN_OPTION + " TEXT,"+
                    COLUMN_AMOUNT + " TEXT)";


    public PDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called when the database needs to be upgraded.
        // For now, we'll just drop and recreate the table.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);
        onCreate(db);
    }


    public void insertPayment(String date, String number, String name, String selectedOption, String amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_NAME, number);
        values.put(COLUMN_NUMBER, name);
        values.put(COLUMN_OPTION, selectedOption);
        values.put(COLUMN_AMOUNT, amount);
        try {
            db.insertOrThrow(TABLE_PAYMENT, null, values);
            Log.d("Database", "Payment inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

}
