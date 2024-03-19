package com.example.bill;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
        replaceFragment(new Fragment_home()
        );

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == MENU_HOME) {
                    replaceFragment(new Fragment_home()
                    );
                    return true;
                } else if (itemId == MENU_PROFILE) {
                    replaceFragment(new fragment_profile());
                    return true;
                }
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)

                .commit();
    }
}



