package com.example.bill;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaleInvoiceActivity extends AppCompatActivity {

    private TextInputEditText invoiceNumEditText;
    private TextInputEditText dateEditText;
    private TextInputEditText Address;
    private ImageButton imageButton;
    private SItemDbHelper dbHelper;
    private Button addItemButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_invoice);

        try {
            dbHelper = new SItemDbHelper(this);

            // Initialize views
            invoiceNumEditText = findViewById(R.id.invoice_num);
            dateEditText = findViewById(R.id.Date);
            imageButton = findViewById(R.id.imageButton);
            addItemButton = findViewById(R.id.addItemButton);
            textView = findViewById(R.id.textView);
            Address = findViewById(R.id.address);
            // Generate and set invoice number
            generateAndSetInvoiceNumber();

            // Set OnClickListener for the date EditText
            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });

            // Set OnClickListener for the ImageButton
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Fetch and show list of companies
                    List<String> companies = fetchCompanies();
                    showCompanyList(companies);
                }
            });

            // Set OnClickListener for the add item button
            // Set OnClickListener for the add item button
            addItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start NewActivity using the context of the view's context
                    Context context = v.getContext();
                    Intent intent = new Intent(context, NewActivity.class);
                    // Pass necessary data to the NewActivity using intent extras
                    intent.putExtra("invoice_number", invoiceNumEditText.getText().toString());
                    intent.putExtra("selected_date", dateEditText.getText().toString());

                    intent.putExtra("client_name", textView.getText().toString()); // Assuming you have a TextView named textView that displays the selected company name
                    intent.putExtra("address", Address.getText().toString()); // Replace "Your address data here" with the actual address data
                    // Start the NewActivity
                    context.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate invoice number
    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis(); // Example: INV-164598436745
    }

    // Generate and set invoice number
    private void generateAndSetInvoiceNumber() {
        try {
            String invoiceNumber = generateInvoiceNumber();
            invoiceNumEditText.setText(invoiceNumber);
            invoiceNumEditText.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show DatePickerDialog
    private void showDatePickerDialog() {
        try {
            // Get current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create and show DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(SaleInvoiceActivity.this,
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
        ClientDbHelper clientDbHelper = new ClientDbHelper(this); // Instantiate ClientDbHelper locally
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
                        TextView textView = findViewById(R.id.textView); // Replace with your TextView ID
                        textView.setText(selectedCompany);
                    }
                });
        builder.create().show();
    }
}
