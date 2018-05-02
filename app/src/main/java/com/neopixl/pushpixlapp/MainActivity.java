package com.neopixl.pushpixlapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neopixl.pushpixl.PushpixlManager;
import com.neopixl.pushpixl.model.UserPreferences;

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
        UserPreferences preferences = new UserPreferences("user123");

        PushpixlManager.getInstance().updateUserPreferences(preferences);
        // END
        // ***
    }
}
