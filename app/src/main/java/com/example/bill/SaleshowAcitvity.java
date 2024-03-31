package com.example.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SaleshowAcitvity extends AppCompatActivity implements SaleAdapter.SaleClickListener {

    private RecyclerView recyclerView;
    private SaleAdapter adapter;
    private List<Sales> salesList; // Updated variable name to salesList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        salesList = new ArrayList<>(); // Initialize salesList
        adapter = new SaleAdapter(salesList, this); // Pass salesList to the adapter
        recyclerView.setAdapter(adapter);
        loadSalesFromDatabase(); // Load sales from the database and update RecyclerView
    }

    private void loadSalesFromDatabase() {
        salesList.clear(); // Clear salesList before adding sales
        SItemDbHelper dbHelper = new SItemDbHelper(this); // Change SalesDbHelper to SItemDbHelper
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SItemContract.ItemEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Sales sales = new Sales();
                sales.setId(cursor.getLong(cursor.getColumnIndex(SItemContract.ItemEntry._ID)));
                sales.setInvoiceNumber(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER)));
                sales.setDate(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_DATE)));
                sales.setClientName(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME)));
                sales.setAddress(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_ADDRESS)));
                sales.setProduct(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_PRODUCT)));
                sales.setQuantity(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_QTY)));
                sales.setUnit(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_UNIT)));
                sales.setPrice(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_PRICE)));
                sales.setGst(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_GST)));
                sales.setDiscount(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_DISCOUNT)));
                sales.setAmount(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_AMOUNT)));
                sales.setTotalAmount(cursor.getString(cursor.getColumnIndex(SItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT)));

                salesList.add(sales);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        adapter.notifyDataSetChanged(); // Notify adapter after loading sales
    }

    @Override

    public void onDeleteClick(Sales sales) {
        // Delete the sale from the database
        deleteSaleFromDatabase(sales.getId());
        // Reload sales from the database and update RecyclerView
        loadSalesFromDatabase();
    }

    private void deleteSaleFromDatabase(long saleId) {
        // Implement your code to delete the sale with the specified ID from the database
        SItemDbHelper dbHelper = new SItemDbHelper(this); // Change SalesDbHelper to SItemDbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SItemContract.ItemEntry.TABLE_NAME, SItemContract.ItemEntry._ID + "=?",
                new String[]{String.valueOf(saleId)});
        db.close();
    }
}
