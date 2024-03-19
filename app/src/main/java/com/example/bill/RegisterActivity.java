package com.example.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;



public class RegisterActivity extends AppCompatActivity {

    public EditText shopNameEditText, phoneEditText, emailEditText, passwordEditText, addressEditText, pinCodeEditText, gstNumberEditText;
    public ImageButton passwordToggle;
    public boolean isPasswordVisible = false;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        shopNameEditText = findViewById(R.id.shopName);
        phoneEditText = findViewById(R.id.phoneNumber);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        addressEditText = findViewById(R.id.address);
        pinCodeEditText = findViewById(R.id.pinCode);
        gstNumberEditText = findViewById(R.id.gstNumber);
        Button submitButton = findViewById(R.id.submitButton);
        passwordToggle = findViewById(R.id.passwordToggle);

        databaseHelper = new DatabaseHelper(this);

        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopName = shopNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String pinCode = pinCodeEditText.getText().toString();
                String gstNumber = gstNumberEditText.getText().toString();

                if (shopName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || pinCode.isEmpty() || gstNumber.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("shop_name", shopName);
                    values.put("phone", phone);
                    values.put("email", email);
                    values.put("password", password);
                    values.put("address", address);
                    values.put("pin_code", pinCode);
                    values.put("gst_number", gstNumber);
                    long newRowId = db.insert("User", null, values);
                    db.close();

                    if (newRowId != -1) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            passwordToggle.setImageResource(R.drawable.ic_visibility_off);
            isPasswordVisible = false;
        } else {
            passwordEditText.setTransformationMethod(null);
            passwordToggle.setImageResource(R.drawable.ic_visibility);
            isPasswordVisible = true;
        }
    }
}
