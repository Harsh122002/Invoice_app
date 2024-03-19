package com.example.bill;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    public EditText emailEditText;
    public EditText newPasswordEditText;
    public DatabaseHelper databaseHelper;
    public ImageView passwordToggle;
    public boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.emailEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
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
                // Get the email and new password from the EditText fields
                String email = emailEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();

                // Check if the email or password is empty
                if (email.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email and new password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the password in the database
                if (updatePasswordInDatabase(email, newPassword)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean updatePasswordInDatabase(String email, String newPassword) {
        try {
            // Get writable database
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            // Prepare content values to update
            ContentValues values = new ContentValues();
            values.put("password", newPassword); // Assuming "password" is the column name in your table

            // Update the database
            int rowsAffected = db.update("User", values, "email=?", new String[]{email});

            // Close the database
            db.close();

            // Return true if at least one row was affected
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void togglePasswordVisibility() {
        if (isPasswordVisible) {
            newPasswordEditText.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            passwordToggle.setImageResource(R.drawable.ic_visibility_off);
            isPasswordVisible = false;
        } else {
            newPasswordEditText.setTransformationMethod(null);
            passwordToggle.setImageResource(R.drawable.ic_visibility);
            isPasswordVisible = true;
        }
    }
}
