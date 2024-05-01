package com.example.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FrontActivity extends AppCompatActivity {

    private static final int MENU_HOME = R.id.menu_home;
    private static final int MENU_PROFILE = R.id.menu_profile;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == MENU_HOME) {
                    startActivity(new Intent(FrontActivity.this, HomeActivity.class));
                    finish(); // Finish the current activity
                    return true;
                } else if (itemId == MENU_PROFILE) {
                    startActivity(new Intent(FrontActivity.this, ProfileActivity.class));
                    finish(); // Finish the current activity
                    return true;
                }
                return false;
            }
        });

        // Set default selected item
        bottomNavigationView.setSelectedItemId(MENU_HOME);
    }
}
