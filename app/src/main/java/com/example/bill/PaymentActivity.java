package com.example.bill;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    private EditText RNumber,Amount;
    private EditText editTextDate;
    private Spinner spinner2;
    private ImageButton imageButton;
    private TextView client;

    private Button saveButton, pdfButton;

    private PDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dbHelper = new PDatabaseHelper(this);


        // Find views by their respective IDs
        client = findViewById(R.id.textView3);
        RNumber = findViewById(R.id.textView4);
        Amount = findViewById(R.id.textView5);
        editTextDate = findViewById(R.id.editTextDate);
        spinner2 = findViewById(R.id.spinner2);
        saveButton = findViewById(R.id.button);
        imageButton = findViewById(R.id.myImageButton);

        pdfButton = findViewById(R.id.button3);

        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientText = client.getText().toString();
                String rNumberText = RNumber.getText().toString();
                String amountText = Amount.getText().toString();
                String dateText = editTextDate.getText().toString();
                String selectedOption = spinner2.getSelectedItem().toString();

                // Check if any of the fields are empty
                if (TextUtils.isEmpty(clientText) || TextUtils.isEmpty(rNumberText) || TextUtils.isEmpty(amountText) || TextUtils.isEmpty(dateText) || TextUtils.isEmpty(selectedOption)) {
                    // At least one field is empty, show a message
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit the method, don't proceed further
                }

                // All fields are filled, proceed with download
                try {
                    initiateDownload(clientText, rNumberText, amountText, dateText, selectedOption);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Fetch and show list of companies
                    List<String> companies = fetchCompanies();
                    showCompanyList(companies);
                }
            });

        int randomNumber = generateRandomNumber(100, 999);
        RNumber.setText(String.valueOf(randomNumber));



        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showDatePickerDialog();
            }

//             imageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Fetch and show list of companies
//                    List<String> companies = fetchCompanies();
//                    showCompanyList(companies);
//                }
//            });

            private void showDatePickerDialog() {
                try {
                    // Get current date
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    // Create and show DatePickerDialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    // Update EditText with selected date
                                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    editTextDate.setText(selectedDate);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(spinner2 != null) {
            List<String> options = new ArrayList<>();
            options.add("Add Cash");
            options.add("Transfer");
            options.add("UPI");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateText = editTextDate.getText().toString();
                String clientText = client.getText().toString();
                String rNumberText = RNumber.getText().toString();

                // Check if any of the required fields are empty
                if (dateText.isEmpty() || clientText.isEmpty() || rNumberText.isEmpty()) {
                    // Show a message indicating that all fields are required
                    Toast.makeText(PaymentActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                    return; // Exit the method early
                }
                else {
                    saveToDatabase();
                    Intent intent = new Intent(PaymentActivity.this,Fragment_home.class);
                    startActivity(intent);

                }
            }
        });
    }

    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private void saveToDatabase() {
        String date = editTextDate.getText().toString();
        String name = client.getText().toString();
        String number = RNumber.getText().toString();
        String selectedOption = spinner2.getSelectedItem().toString();
        String amount = Amount.getText().toString();

        dbHelper.insertPayment(date,number,name, selectedOption,amount);

        Toast.makeText(this, "Payment saved to database", Toast.LENGTH_SHORT).show();
    }



    private List<String> fetchCompanies() {
        List<String> companies = new ArrayList<>();
        ClientDbHelper clientDbHelper = new ClientDbHelper(this); // Instantiate ClientDbHelper locally
        SQLiteDatabase db = clientDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM " + ClientContract.ClientEntry.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String companyName = cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_NAME));
//                String gstNumber = cursor.getString(cursor.getColumnIndex(ClientContract.ClientEntry.COLUMN_NAME_GST_NUMBER));
                String companyInfo = companyName ;
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
                        TextView textView = findViewById(R.id.textView3); // Replace with your TextView ID
                        textView.setText(selectedCompany);
                    }
                });
        builder.create().show();
    }


    private void initiateDownload(String clientText, String rNumberText, String amountText, String dateText, String selectedOption) throws IOException {
        // Create a new PDF document
        PdfDocument document = new PdfDocument();

        // Define the page size and create a page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(612, 900, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the canvas for drawing
        Canvas canvas = page.getCanvas();

        // Define the paint properties
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        // Calculate the initial position to draw the first table header
        float x = 50;
        float y = 200;

        String text="Payment Receipt";
        canvas.drawText(text,x+250,y,paint);
        y+=10;
        String text2="Name:"+" "+clientText;
        canvas.drawText(text2,x,y+30,paint);
        y+=20;
        String text3="Date:"+" "+dateText;
        canvas.drawText(text3,x,y+30,paint);
    y+=20;
        String text4="Receipt Number:"+" "+rNumberText;
        canvas.drawText(text4,x,y+30,paint);
        y+=20;
        String text7="Receipt Number:"+" "+selectedOption;
        canvas.drawText(text7,x,y+30,paint);
        y+=20;
        String text5="Amount"+" "+amountText;
        canvas.drawText(text5,x,y+30,paint);




        // Adjust the y-coordinate for next drawing
      // Add some spacing between header and data

        // Loop through itemData to draw each item's details in the first table

        String text6="This Bill Computer is Generated Therefore Sign is not Required";
        canvas.drawText(text6,x+75,y+200,paint);

        // Finish the page
        document.finishPage(page);

        // Define the file to save the PDF
        File file = new File(getFilesDir(), "my_pdf_document.pdf");

        // Write the PDF content to the file
        FileOutputStream fos = new FileOutputStream(file);
        document.writeTo(fos);
        document.close();
        fos.close();

        // Display a toast message indicating success
        Toast.makeText(this, "PDF created successfully", Toast.LENGTH_SHORT).show();

        // Start the download process
        startDownload(file);
    }


    private void startDownload(File file) {
        // Once the PDF is created, initiate the download process

        // Create an intent to view the PDF
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Get the URI for the file using FileProvider
        Uri uri = FileProvider.getUriForFile(PaymentActivity.this, "com.example.bill.fileprovider", file);

        // Set the data and type for the intent
        intent.setDataAndType(uri, "application/pdf");

        // Add the FLAG_GRANT_READ_URI_PERMISSION flag
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        // Start the activity to view the PDF
        startActivity(intent);

    }
}


