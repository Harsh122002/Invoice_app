package com.example.bill;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ClientAddActivity extends AppCompatActivity {

    private EditText editTextClientName, editTextGSTNumber, editTextMobileNumber, editTextEmailId, editTextAddress;
    private RadioGroup radioGroupRole;
    private Button buttonAddClient;

    private ClientDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add);

        // Initialize views
        editTextClientName = findViewById(R.id.editTextClientName);
        editTextGSTNumber = findViewById(R.id.editTextGSTNumber);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextEmailId = findViewById(R.id.editTextEmailId);
        editTextAddress = findViewById(R.id.editTextAddress);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        buttonAddClient = findViewById(R.id.buttonAddClient);

        dbHelper = new ClientDbHelper(this);

        // Set click listener for the "Add Client" button
        buttonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values from the EditText fields
                String clientName = editTextClientName.getText().toString().trim();
                String gstNumber = editTextGSTNumber.getText().toString().trim();
                String mobileNumber = editTextMobileNumber.getText().toString().trim();
                String emailId = editTextEmailId.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();

                // Check if any field is empty
                if (clientName.isEmpty() || gstNumber.isEmpty() || mobileNumber.isEmpty() || emailId.isEmpty() || address.isEmpty()) {
                    // Display message asking the user to fill in all fields
                    Toast.makeText(getApplicationContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Validate mobile number (should have exactly 10 digits)
                if (mobileNumber.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Mobile number should have exactly 10 digits.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Validate email format (ending with "@company.com")
                if (!emailId.matches(".*@company\\.com$")) {
                    Toast.makeText(getApplicationContext(), "Email should end with '@company.com'", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // If all validations pass, proceed to insert client
                insertClient();
                Intent intent = new Intent(ClientAddActivity.this, Fragment_home.class);
                startActivity(intent);
            }
        });

    }

    private void insertClient() {
        // Get the data from the input fields
        String clientName = editTextClientName.getText().toString().trim();
        String gstNumber = editTextGSTNumber.getText().toString().trim();
        String mobileNumber = editTextMobileNumber.getText().toString().trim();
        String emailId = editTextEmailId.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String role = ((RadioButton) findViewById(radioGroupRole.getCheckedRadioButtonId())).getText().toString();

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ClientContract.ClientEntry.COLUMN_NAME_NAME, clientName);
        values.put(ClientContract.ClientEntry.COLUMN_NAME_ROLE, role);
        values.put(ClientContract.ClientEntry.COLUMN_NAME_GST_NUMBER, gstNumber);
        values.put(ClientContract.ClientEntry.COLUMN_NAME_MOBILE_NUMBER, mobileNumber);
        values.put(ClientContract.ClientEntry.COLUMN_NAME_EMAIL, emailId);
        values.put(ClientContract.ClientEntry.COLUMN_NAME_ADDRESS, address);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ClientContract.ClientEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            // Insertion successful
            Toast.makeText(this, "Client added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        } else {
            // Insertion failed
            Toast.makeText(this, "Failed to add client", Toast.LENGTH_SHORT).show();
        }
    }
}
