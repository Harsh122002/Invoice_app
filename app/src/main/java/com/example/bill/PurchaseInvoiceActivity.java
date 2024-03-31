package com.example.bill;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseInvoiceActivity extends AppCompatActivity {

    private TextInputEditText invoiceNumEditText;
    private TextInputEditText dateEditText;
    private TextInputEditText AddressView;
    private ImageButton imageButton;
    private ClientDbHelper clientDbHelper; // Changed to ClientDbHelper
    private Button addItemButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        try {
            // Initialize views
            invoiceNumEditText = findViewById(R.id.invoice_num);
            dateEditText = findViewById(R.id.Date);
            imageButton = findViewById(R.id.imageButton);
            addItemButton = findViewById(R.id.addItemButton);
            textView = findViewById(R.id.textView);
            AddressView = findViewById(R.id.address);

            // Instantiate ClientDbHelper for fetching companies
            clientDbHelper = new ClientDbHelper(this);

            // Generate and set invoice number


            // Set OnClickListener for the date EditText
            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

            // Set OnClickListener for the ImageButton to select companies
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Fetch and show list of companies
                    List<String> companies = fetchCompanies();
                    showCompanyList(companies);
                }
            });

            // Set OnClickListener for the add item button
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve necessary data from EditText and TextView fields
                    String invoiceNumber = invoiceNumEditText.getText().toString();
                    String selectedDate = dateEditText.getText().toString();
                    String clientName = textView.getText().toString();
                    String address = AddressView.getText().toString();

                    // Check if any of the necessary data is empty
                    if (TextUtils.isEmpty(invoiceNumber) || TextUtils.isEmpty(selectedDate) ||
                            TextUtils.isEmpty(clientName) || TextUtils.isEmpty(address)) {
                        // If any data is empty, show a toast message and return
                        Toast.makeText(PurchaseInvoiceActivity.this, "Please fill all necessary data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Start NewActivity and pass necessary data
                    Intent intent = new Intent(PurchaseInvoiceActivity.this, Pnew_activity.class);
                    intent.putExtra("invoice_number", invoiceNumber);
                    intent.putExtra("selected_date", selectedDate);
                    intent.putExtra("client_name", clientName);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate invoice number


    // Generate and set invoice number


    // Show DatePickerDialog
    private void showDatePickerDialog() {
        try {
            // Get current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create and show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(PurchaseInvoiceActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            // Update EditText with selected date
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            dateEditText.setText(selectedDate);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch list of companies from database
    private List<String> fetchCompanies() {
        List<String> companies = new ArrayList<>();
        SQLiteDatabase db = clientDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, gst_number FROM " + ClientContract.ClientEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String companyName = cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_NAME));
                String gstNumber = cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_GST_NUMBER));
                String companyInfo = companyName + "\n\n" + "GST : " + gstNumber;
                companies.add(companyInfo);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Close the database connection
        db.close();

        return companies;
    }

    // Show list of companies in AlertDialog
    private void showCompanyList(List<String> companies) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a Company")
                .setItems(companies.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update TextView with selected company name
                        String selectedCompany = companies.get(which);
                        textView.setText(selectedCompany);
                    }
                });
        builder.create().show();
    }
}
