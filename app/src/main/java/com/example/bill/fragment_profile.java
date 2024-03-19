package com.example.bill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment_profile extends Fragment {

    private TextView titleTextView;
    private EditText shopNameEditText;
    private EditText gstNoEditText;
    private EditText mobileEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText passwordEditText;
    private EditText pincodeEditText;
    private Button updateButton;

    private String enteredEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        titleTextView = view.findViewById(R.id.titleTextView);
        shopNameEditText = view.findViewById(R.id.shopNameTextView);
        gstNoEditText = view.findViewById(R.id.gstNoTextView);
        mobileEditText = view.findViewById(R.id.mobileTextView);
        emailEditText = view.findViewById(R.id.emailTextView);
        addressEditText = view.findViewById(R.id.addressTextView);
        passwordEditText = view.findViewById(R.id.passwordTextView);
        pincodeEditText = view.findViewById(R.id.pincodeTextView);
        updateButton = view.findViewById(R.id.Profile_update);

        Intent intent = getActivity().getIntent();
        enteredEmail = intent.getStringExtra("key_email");
        getUser();

// Get the shop name from the EditText
        String shopName = shopNameEditText.getText().toString();
        String Gst = gstNoEditText.getText().toString();
        String Address = addressEditText.getText().toString();


// Save the shop name to SharedPreferences
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("SHOP_NAME", shopName);
        editor.putString("SHOP_Gst", Gst);
        editor.putString("SHOP_Address", Address);

        editor.apply();



        // Call method to fetch and display user details
//
//        Intent intent1 = new Intent(getActivity(), view_activity.class);
//        intent1.putExtra("shopNameEditText", shopNameEditText.getText().toString());
//        intent1.putExtra("addressEditText", addressEditText.getText().toString());
//        startActivity(intent1);




        // Set onClickListener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });

        return view;
    }

    public void getUser() {
        if (enteredEmail == null) {
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
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

        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        boolean success = dbHelper.updateUserDetails( enteredEmail,shopName, mobile,email,address, password, pincode, gstNo);

        if (success) {
            Toast.makeText(getActivity(), "Details updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Failed to update details", Toast.LENGTH_SHORT).show();
        }
    }
}
