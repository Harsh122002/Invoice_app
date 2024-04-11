package com.example.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemShowActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Items> itemList; // Change variable name to itemList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemviews);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>(); // Initialize itemList
        adapter = new ItemAdapter(getItemFromDatabase(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<Items> getItemFromDatabase() {
        itemList.clear(); // Clear itemList before adding items
        ItemDbHelper dbHelper = new ItemDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ItemContract.ItemEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Items items = new Items();
                items.setId(cursor.getLong(cursor.getColumnIndex(ItemContract.ItemEntry._ID)));
                items.setName(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME_NAME)));
                items.setPrice(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME_PRICE)));
                items.setDiscount(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME_DISCOUNT)));
                items.setGst(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME_GST_TAX)));
                items.setqty(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME_QTY)));


                itemList.add(items);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return itemList;
    }

    @Override
    public void onDeleteClick(Items items) {
        // Delete the item from the database
        deleteItemFromDatabase(items.getId());
        // Update RecyclerView after deletion
        adapter.updateData(getItemFromDatabase());
    }

    private void deleteItemFromDatabase(long itemId) {
        // Implement your code to delete the item with the specified ID from the database
        ItemDbHelper dbHelper = new ItemDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ItemContract.ItemEntry.TABLE_NAME, ItemContract.ItemEntry._ID + "=?",
                new String[]{String.valueOf(itemId)});
        db.close();
    }
}
