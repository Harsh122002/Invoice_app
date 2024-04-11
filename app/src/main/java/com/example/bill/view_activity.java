package com.example.bill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class view_activity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private Button buttonAdd1;
    private Button buttonAdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        buttonAdd1 = findViewById(R.id.buttonpdf);
        buttonAdd2 = findViewById(R.id.buttonhome);




        buttonAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_activity.this, FrontActivity.class);
                startActivity(intent);

            }
        });

        buttonAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Your existing code...
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    String invoice = prefs.getString("SHOP_invoice", "");
                    String shopName=prefs.getString("SHOP_NAME","");
                    String Address=prefs.getString("SHOP_Address","");
                    String Gst=prefs.getString("SHOP_Gst","");

                    // Generate the PDF document
                    List<Item> itemData=fetchItems(invoice);
                    initiateDownload(invoice,itemData,shopName,Address,Gst);

                    // Show a toast message indicating PDF generation
                    Toast.makeText(view_activity.this, "PDF generated and downloaded", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(view_activity.this, "Error occurred while generating PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    // Method to initiate PDF download
    private void initiateDownload(final String invoice, final List<Item> itemData, final String shopName, final String Address, final String Gst) throws IOException {
        // Create a handler with a delay of 15 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inside the delayed action, create the PDF and start the download
                try {
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
                    String text = shopName;
                    canvas.drawText(text, x + 250, y, paint);
                    String text1 = Address;
                    canvas.drawText(text1, x + 250, y + 20, paint);
                    String text3 = Gst;
                    canvas.drawText(text3, x + 250, y + 40, paint);
                    String text4 = invoice;
                    canvas.drawText(text4, x, y + 60, paint);

                    y += 100;

                    // Draw the first table header
                    canvas.drawText("Client Name", x, y, paint);
                    canvas.drawText("Address", x + 200, y, paint);
                    canvas.drawText("Date", x + 400, y, paint);

                    // Adjust the y-coordinate for next drawing
                    y += paint.getTextSize() + 5; // Add some spacing between header and data

                    // Loop through itemData to draw each item's details in the first table
                    for (Item item : itemData) {
                        // Draw item details in the first table format
                        canvas.drawText(item.getClientName(), x, y, paint);
                        canvas.drawText(item.getAddress(), x + 200, y, paint);
                        canvas.drawText(item.getDate(), x + 400, y, paint);

                        // Adjust the y-coordinate for next row
                        y += paint.getTextSize() + 5; // Add some spacing between rows
                    }

                    // Adjust the y-coordinate for spacing between tables
                    y += 20; // Add spacing between tables

                    // Draw the second table header
                    canvas.drawText("Product", x, y, paint);
                    canvas.drawText("Qty", x + 100, y, paint);
                    canvas.drawText("Unit", x + 120, y, paint);
                    canvas.drawText("Price", x + 170, y, paint);
                    canvas.drawText("Gst", x + 220, y, paint);
                    canvas.drawText("Discount", x + 250, y, paint);
                    canvas.drawText("Amount", x + 330, y, paint);
                    canvas.drawText("Total Amount", x + 420, y, paint);

                    // Adjust the y-coordinate for next drawing
                    y += paint.getTextSize() + 5; // Add some spacing between header and data

                    // Loop through itemData to draw each item's details in the second table
                    for (Item item : itemData) {
                        // Draw item details in the second table format
                        canvas.drawText(item.getProduct(), x, y, paint);
                        canvas.drawText(String.valueOf(item.getQuantity()), x + 100, y, paint);
                        canvas.drawText(item.getUnit(), x + 120, y, paint);
                        canvas.drawText(String.valueOf(item.getPrice()), x + 170, y, paint);
                        canvas.drawText(String.valueOf(item.getGst()), x + 220, y, paint);
                        canvas.drawText(String.valueOf(item.getDiscount()), x + 250, y, paint);
                        canvas.drawText(String.valueOf(item.getAmount()), x + 330, y, paint);
                        canvas.drawText(String.valueOf(item.getTotalAmount()), x + 420, y, paint);

                        // Adjust the y-coordinate for next row
                        y += paint.getTextSize() + 5; // Add some spacing between rows
                    }
                    y += 100;
                    String text6 = "This Bill Computer is Generated Therefore Sign is not Required";
                    canvas.drawText(text6, x + 75, y + 200, paint);

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
                    Toast.makeText(getApplicationContext(), "PDF created successfully", Toast.LENGTH_SHORT).show();

                    // Start the download process
                    startDownload(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1500); // 15 seconds delay (in milliseconds)
    }


    private void startDownload(File file) {
        // Once the PDF is created, initiate the download process

        // Create an intent to view the PDF
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // Get the URI for the file using FileProvider
        Uri uri = FileProvider.getUriForFile(view_activity.this, "com.example.bill.fileprovider", file);

        // Set the data and type for the intent
        intent.setDataAndType(uri, "application/pdf");

        // Add the FLAG_GRANT_READ_URI_PERMISSION flag
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the activity to view the PDF
        startActivity(intent);
    }    private List<Item> fetchItems(String invoiceNumber) {
        try {
            List<Item> itemData = new ArrayList<>();

            // Get readable database
            SItemDbHelper dbHelper = new SItemDbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Query to retrieve data for a specific invoice number
            String[] projection = {"date", "client_name", "address", "product", "qty", "unit", "price", "gst", "discount", "amount", "total_amount"};
            String selection = "invoice_number = ?";
            String[] selectionArgs = {invoiceNumber};
            Cursor cursor = db.query("items", projection, selection, selectionArgs, null, null, null);

            // Iterate through the cursor to fetch item data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extract fields from cursor
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String clientName = cursor.getString(cursor.getColumnIndex("client_name"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String product = cursor.getString(cursor.getColumnIndex("product"));
                    int quantity = cursor.getInt(cursor.getColumnIndex("qty"));
                    String unit = cursor.getString(cursor.getColumnIndex("unit"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    double gst = cursor.getDouble(cursor.getColumnIndex("gst"));
                    double discount = cursor.getDouble(cursor.getColumnIndex("discount"));
                    double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                    double totalAmount = cursor.getDouble(cursor.getColumnIndex("total_amount"));

                    // Create Item object and add to list
                    Item item = new Item(date, clientName, address, product, quantity, unit, price, gst, discount, amount, totalAmount);
                    itemData.add(item);
                } while (cursor.moveToNext());

                // Close the cursor after use
                cursor.close();
            }

            // Close the database connection
            db.close();

            return itemData;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occurred while fetching item data", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }




}






