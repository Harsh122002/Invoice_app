package com.example.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PurchaseshowActivity extends AppCompatActivity implements PurchaseAdapter.PurchaseClickListener {

    private RecyclerView recyclerView;
    private PurchaseAdapter adapter;
    private List<Purchase> purchaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchaseList = new ArrayList<>(); // Initialize purchaseList
        adapter = new PurchaseAdapter(purchaseList, this); // Pass purchaseList to the adapter
        recyclerView.setAdapter(adapter);
        loadPurchaseFromDatabase(); // Load purchase from the database and update RecyclerView
    }

    private void loadPurchaseFromDatabase() {
        purchaseList.clear(); // Clear purchaseList before adding purchase
        PItemDbHelper dbHelper = new PItemDbHelper(this); // Change SItemDbHelper to PItemDbHelper
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PItemContract.ItemEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Purchase purchase = new Purchase();
                purchase.setId(cursor.getLong(cursor.getColumnIndex(PItemContract.ItemEntry._ID)));
                purchase.setInvoiceNumber(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_INVOICE_NUMBER)));
                purchase.setDate(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_DATE)));
                purchase.setClientName(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_CLIENT_NAME)));
                purchase.setAddress(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_ADDRESS)));
                purchase.setProduct(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_PRODUCT)));
                purchase.setQuantity(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_QTY)));
                purchase.setUnit(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_UNIT)));
                purchase.setPrice(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_PRICE)));
                purchase.setGst(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_GST)));
                purchase.setDiscount(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_DISCOUNT)));
                purchase.setAmount(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_AMOUNT)));
                purchase.setTotalAmount(cursor.getString(cursor.getColumnIndex(PItemContract.ItemEntry.COLUMN_NAME_TOTAL_AMOUNT)));

                purchaseList.add(purchase);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        adapter.notifyDataSetChanged(); // Notify adapter after loading purchase
    }

    @Override
    public void onDeleteClick(Purchase purchase) {
        // Delete the purchase from the database
        deletePurchaseFromDatabase(purchase.getId());
        // Reload purchase from the database and update RecyclerView
        loadPurchaseFromDatabase();
    }

    private void deletePurchaseFromDatabase(long purchaseId) {
        // Implement your code to delete the purchase with the specified ID from the database
        PItemDbHelper dbHelper = new PItemDbHelper(this); // Change SItemDbHelper to PItemDbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PItemContract.ItemEntry.TABLE_NAME, PItemContract.ItemEntry._ID + "=?",
                new String[]{String.valueOf(purchaseId)});
        db.close();
    }
}
