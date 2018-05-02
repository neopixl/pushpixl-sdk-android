package com.neopixl.pushpixlapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.neopixl.pushpixl.PushpixlManager;
import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.listener.ReadConfirmationListener;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.listener.UserPreferencesRemoveListener;
import com.neopixl.pushpixl.model.QuietTime;
import com.neopixl.pushpixl.model.UserPreferences;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.update_prefs);
        button.setOnClickListener(v -> updateUserPreferences());

        Button reloadButton = findViewById(R.id.reload_prefs);
        reloadButton.setOnClickListener(v -> reloadUserPreferences());

        Button removeButton = findViewById(R.id.remove_prefs);
        removeButton.setOnClickListener(v -> removeUserPreferences());

        // ***
        // ADDED FOR LIBRARY
        if (getIntent().getExtras() != null) {
            RemoteMessage remoteMessage = getIntent().getExtras().getParcelable("remote_message");
            if (remoteMessage != null) {
                PushpixlManager.getInstance().confirmReading(remoteMessage, new ReadConfirmationListener() {
                    @Override
                    public void onMessageMarkedAsReadSuccess(String s, String s1) {
                        Toast.makeText(MainActivity.this, "Notification marked read", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMessageMarkedAsReadError(String s, PushpixlException e) {
                        Log.d(TAG, "Error occur", e);
                        Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        // END
        // ***
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
                Log.d(TAG, "Error occur", e);
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        // END
        // ***
    }

    public void reloadUserPreferences() {
        // ***
        // ADDED FOR LIBRARY
        PushpixlManager.getInstance().reloadUserPreferences(new UserPreferencesListener() {
            @Override
            public void onUserPreferencesUpdate(String s, UserPreferences userPreferences) {
                Toast.makeText(MainActivity.this, "Preference updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserPreferencesError(UserPreferences userPreferences, PushpixlException e) {
                Log.d(TAG, "Error occur", e);
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        // END
        // ***
    }

    public void removeUserPreferences() {
        // ***
        // ADDED FOR LIBRARY
        PushpixlManager.getInstance().removeUserPreferences(new UserPreferencesRemoveListener() {
            @Override
            public void onUserPreferencesRemoved(String s) {
                Toast.makeText(MainActivity.this, "Preference removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserPreferencesRemoveError(PushpixlException e) {
                Log.d(TAG, "Error occur", e);
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        // END
        // ***
    }
}
