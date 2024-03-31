package com.example.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;


public class DataShowActivity extends AppCompatActivity implements ClientAdapter.ClientClickListener {

    private RecyclerView recyclerView;
    private ClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientAdapter(getClientsFromDatabase(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<Client> getClientsFromDatabase() {
        List<Client> clients = new ArrayList<>();
        ClientDbHelper dbHelper = new ClientDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ClientContract.ClientEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Client client = new Client();
                client.setId(cursor.getLong(cursor.getColumnIndex(ClientContract.ClientEntry._ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_NAME)));
                client.setRole(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_ROLE)));
                client.setGstNumber(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_GST_NUMBER)));
                client.setMobileNumber(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_MOBILE_NUMBER)));
                client.setEmail(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_EMAIL)));
                client.setAddress(cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_ADDRESS)));
                clients.add(client);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return clients;
    }

    @Override
    public void onDeleteClick(Client client) {
        // Delete the client from the database
        deleteClientFromDatabase(client.getId());
        // Update RecyclerView after deletion
        adapter.updateData(getClientsFromDatabase());
    }

    private void deleteClientFromDatabase(long clientId) {
        // Implement your code to delete the client with the specified ID from the database
        ClientDbHelper dbHelper = new ClientDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ClientContract.ClientEntry.TABLE_NAME, ClientContract.ClientEntry._ID + "=?",
                new String[]{String.valueOf(clientId)});
        db.close();
    }
}
