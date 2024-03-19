package com.example.bill;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class dropActivity extends AppCompatActivity {

    private ClientDbHelper dbHelper; // Instance of your ClientDbHelper class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_invoice); // Replace "your_layout" with the layout file name

        // Initialize dbHelper
        dbHelper = new ClientDbHelper(this);

        // Find the ImageButton by its ID
        ImageButton imageButton = findViewById(R.id.imageButton);

        // Set an OnClickListener to the ImageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch companies from the database
                List<String> companies = fetchCompanies();

                // Show the list of companies in an AlertDialog
                showCompanyList(companies);
            }
        });
    }

    // Method to fetch companies from the database
    private List<String> fetchCompanies() {
        List<String> companies = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM clients", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String company = cursor.getString(cursor.getColumnIndex("name"));
                companies.add(company);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return companies;
    }

    // Method to show the list of companies in an AlertDialog
    private void showCompanyList(List<String> companies) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Company")
                .setItems(companies.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Retrieve the selected company
                        String selectedCompany = companies.get(which);

                        // Display the selected company name in a TextView
                        TextView textView = findViewById(R.id.textView); // Replace with your TextView ID
                        textView.setText(selectedCompany);
                    }
                });
        builder.create().show();
    }
}
