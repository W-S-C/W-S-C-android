package com.example.weather_community_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.weather_community_android.R;
import com.example.weather_community_android.fragment.FeedFragment;
import com.example.weather_community_android.fragment.MainFragment;
import com.example.weather_community_android.fragment.MyPageFragment;
import com.example.weather_community_android.fragment.NotifyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, new MainFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new MainFragment()).commit();
                        break;
                    case R.id.item_feed:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new FeedFragment()).commit();
                        break;
                    case R.id.item_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new NotifyFragment()).commit();
                        break;
                    case R.id.item_my_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new MyPageFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }
}