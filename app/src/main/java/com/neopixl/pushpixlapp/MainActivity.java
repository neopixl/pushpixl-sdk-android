package com.neopixl.pushpixlapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.neopixl.pushpixl.PushpixlManager;
import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.pushpixl.model.QuietTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.update_prefs);
        button.setOnClickListener(v -> updateUserPreferences());
    }

    public void updateUserPreferences() {
        // ***
        // ADDED FOR LIBRARY
        UserPreferences preferences = new UserPreferences("user123")
                .quietTime(new QuietTime(22,0,5,0));

        PushpixlManager.getInstance().updateUserPreferences(preferences, new UserPreferencesListener() {
            @Override
            public void onUserPreferencesUpdate(String s, UserPreferences userPreferences) {
                Toast.makeText(MainActivity.this, "Preference updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserPreferencesError(UserPreferences userPreferences, PushpixlException e) {
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        // END
        // ***
    }
}
