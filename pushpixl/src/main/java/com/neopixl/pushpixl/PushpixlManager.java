package com.neopixl.pushpixl;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.exception.NoPreferencesException;
import com.neopixl.pushpixl.exception.NoTokenException;
import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.listener.NotificationSendListener;
import com.neopixl.pushpixl.listener.ReadConfirmationListener;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.listener.UserPreferencesRemoveListener;
import com.neopixl.pushpixl.model.PushConfiguration;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.pushpixl.network.NetworkManager;
import com.neopixl.pushpixl.network.util.URLBuilder;
import com.neopixl.pushpixl.util.PushPixlPreferences;

import java.net.URL;
import java.util.Map;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlManager {
    private static PushpixlManager _instance;

    /**
     * Retreive the current instance of the manager installed
     *
     * @return a PushpixlManager
     */
    public static PushpixlManager getInstance() {
        if (_instance == null) {
            throw new IncorrectConfigurationException("Cannot use Pushpixl without installing the configuration `install(context, configuration)`");
        }
        return _instance;
    }

    /**
     * Install the given configuration as the default PushpixlManager
     *
     * @param context the application context
     * @param configuration the configuration for the manager
     * @return the new PushpixlManager
     */
    public static PushpixlManager install(@NonNull Context context, @NonNull PushConfiguration configuration) {
        _instance = new PushpixlManager(context, configuration);

        URLBuilder.setConfiguration(configuration);

        return getInstance();
    }


    @NonNull private Context context;
    @NonNull private PushConfiguration configuration;
    private NetworkManager networkManager;

    /**
     * Default constructor for installation
     *
     * @param context the application context
     * @param configuration the configuration for the manager
     */
    private PushpixlManager(@NonNull Context context, @NonNull PushConfiguration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
        networkManager = new NetworkManager(this.context);
    }

    /**
     *  Get the current used configuration
     * @return the push configuration
     */
    @NonNull
    public PushConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Update the user data on the Pushpixl servers
     *
     * @param preferences the preference of the current user
     */
    public void updateUserPreferences(@NonNull UserPreferences preferences) {
        this.updateUserPreferences(preferences, null);
    }

    /**
     * Update the user data on the Pushpixl servers
     *
     * @param preferences the preference of the current user
     * @param listener a listener to handle success and error
     */
    public void updateUserPreferences(@NonNull UserPreferences preferences, @Nullable UserPreferencesListener listener) {
        Log.i(PushPixlConstant.NP_LOG_TAG, "Updating user preferences");

        PushPixlPreferences.setUserPreferences(context, preferences);

        String token = FirebaseInstanceId.getInstance().getToken();

        if (token == null) {
            Log.i(PushPixlConstant.NP_LOG_TAG, "There is no firebase token");
            if (listener != null) {
                listener.onUserPreferencesError(preferences, new NoTokenException("There is no firebase token, the preferences will be uploaded automaticatly if the configuration is in `autoRefresh` mode"));
            }
            return;
        }

        Log.i(PushPixlConstant.NP_LOG_TAG, "The firebase token already exist");

        networkManager.registerDevice(configuration, preferences, token, listener);
    }

    /**
     * Update the user data on the Pushpixl servers based on the already saved preferences
     */
    public void reloadUserPreferences() {
        this.reloadUserPreferences(null);
    }

    /**
     * Update the user data on the Pushpixl servers based on the already saved preferences
     * @param listener a listener to handle success and error
     */
    public void reloadUserPreferences(@Nullable UserPreferencesListener listener) {
        Log.i(PushPixlConstant.NP_LOG_TAG, "Trying to reload user preferences");

        UserPreferences preferences = PushPixlPreferences.getUserPreferences(context);
        if (preferences == null) {
            Log.i(PushPixlConstant.NP_LOG_TAG, "There is no preferences already saved");
            if (listener != null) {
                listener.onUserPreferencesError(preferences, new NoPreferencesException("There is no preferences already saved"));
            }
            return;
        }

        this.updateUserPreferences(preferences, listener);
    }

    /**
     * Remove the user data on the pushpixl servers based on the firebase token
     */
    public void removeUserPreferences() {
        this.removeUserPreferences(null);
    }

    /**
     * Remove the user data on the pushpixl servers based on the firebase token
     * @param listener a listener to handle success and error
     */
    public void removeUserPreferences(@Nullable final UserPreferencesRemoveListener listener) {
        Log.i(PushPixlConstant.NP_LOG_TAG, "Removing user preferences");

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            Log.i(PushPixlConstant.NP_LOG_TAG, "There is no firebase token");
            if (listener != null) {
                listener.onUserPreferencesRemoveError(new NoTokenException("There is no firebase token, nothing have been sent to the server"));
            }
            return;
        }

        Log.i(PushPixlConstant.NP_LOG_TAG, "The firebase token already exist");

        networkManager.unregisterDevice(configuration, token, new UserPreferencesRemoveListener() {
            @Override
            public void onUserPreferencesRemoved(String token) {
                PushPixlPreferences.setUserPreferences(context, null);

                if (listener != null) {
                    listener.onUserPreferencesRemoved(token);
                }
            }

            @Override
            public void onUserPreferencesRemoveError(PushpixlException exception) {
                if (listener != null) {
                    listener.onUserPreferencesRemoveError(exception);
                }
            }
        });
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     * @param remoteMessage the message
     */
    public void confirmReading(@NonNull RemoteMessage remoteMessage) {
        confirmReading(remoteMessage, null);
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     * @param remoteMessage the message
     * @param listener a listener to handle success and error
     */
    public void confirmReading(@NonNull RemoteMessage remoteMessage, ReadConfirmationListener listener) {
        Map<String, String> data = remoteMessage.getData();
        String messageId = null;
        if (data != null) {
            String messageIdInternal = data.get(PushPixlConstant.DATA_KEY_INTERNAL_ID);
            if (messageIdInternal != null) {
                messageId = messageIdInternal;
            }
        }
        if (messageId != null) {
            confirmReading(messageId, listener);
        }
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     * @param messageId the message ID
     */
    public void confirmReading(@NonNull String messageId) {
        confirmReading(messageId, null);
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     * @param messageId the message ID
     * @param listener a listener to handle success and error
     */
    public void confirmReading(@NonNull String messageId, @Nullable ReadConfirmationListener listener) {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            Log.i(PushPixlConstant.NP_LOG_TAG, "There is no firebase token");
            if (listener != null) {
                listener.onMessageMarkedAsReadError(messageId, new NoTokenException("There is no firebase token, nothing have been sent to the server"));
            }
            return;
        }

        Log.i(PushPixlConstant.NP_LOG_TAG, "handle notification id : "+messageId);
        networkManager.confirmReading(configuration, token, messageId, listener);
    }

    /**
     * Send a push notification via PushPixl, on this device
     * @param message the message to display
     */
    public void pushToMySelf(@NonNull String message, @Nullable NotificationSendListener listener) {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            Log.i(PushPixlConstant.NP_LOG_TAG, "There is no firebase token");
            if (listener != null) {
                listener.onNotificationError(message, new NoTokenException("There is no firebase token, nothing have been sent to the server"));
            }
            return;
        }

        Log.i(PushPixlConstant.NP_LOG_TAG, "Will launch a push to this user : "+ token);
        networkManager.pushToMySelf(configuration, token, message, listener);
    }
}
