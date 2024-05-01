package com.example.bill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private TextView titleTextView;
    private EditText shopNameEditText;
    private EditText gstNoEditText;
    private EditText mobileEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText passwordEditText;
    private EditText pincodeEditText;
    private Button updateButton;
    private BottomNavigationView bottomNavigationView;

    private String enteredEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        shopNameEditText = findViewById(R.id.shopNameEditText);
        gstNoEditText = findViewById(R.id.gstNoEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        pincodeEditText = findViewById(R.id.pincodeEditText);
        updateButton = findViewById(R.id.profileUpdateButton);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        enteredEmail = sharedPreferences.getString("email", "");

        // Set onClickListener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile); // Set profile as selected
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_profile) {
                    return true; // Profile is already selected
                } else if (itemId == R.id.menu_home) {
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    finish(); // Finish the current activity
                    return true;
                }
                return false;
            }
        });

        getUser();
    }

    public void getUser() {
        if (enteredEmail == null) {
            Log.d("getUser", "Entered email is null");
            return;
        }

        Log.d("getUser", "Entered email: " + enteredEmail);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<DatabaseContract> userList = dbHelper.getLogged(enteredEmail);

        if (!userList.isEmpty()) {
            DatabaseContract user = userList.get(0);
            shopNameEditText.setText(user.getShopName());
            gstNoEditText.setText(user.getGstNumber());
            mobileEditText.setText(user.getPhone());
            emailEditText.setText(user.getEmail());
            addressEditText.setText(user.getAddress());
            passwordEditText.setText(user.getPassword());
            pincodeEditText.setText(user.getPinCode());
        } else {
            Log.d("getUser", "User list is empty for email: " + enteredEmail);
        }
    }

    private void updateUserDetails() {
        String shopName = shopNameEditText.getText().toString();
        String gstNo = gstNoEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String pincode = pincodeEditText.getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean success = dbHelper.updateUserDetails(enteredEmail, shopName, mobile, email, address, password, pincode, gstNo);

        if (success) {
            Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update details", Toast.LENGTH_SHORT).show();
        }
    }
}
