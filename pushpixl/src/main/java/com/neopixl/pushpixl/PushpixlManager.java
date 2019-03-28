package com.neopixl.pushpixl;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlManager {
    public static final String NP_LOG_TAG = PushpixlManager.class.getSimpleName();
    public static final String NETWORK_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String NETWORK_FORMAT_DATE_QUIETTIME = "HH:mmZ";

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
     * @param context       the application context
     * @param configuration the configuration for the manager
     * @return the new PushpixlManager
     */
    public static PushpixlManager install(@NonNull Context context, @NonNull PushConfiguration configuration) {
        _instance = new PushpixlManager(context, configuration);

        URLBuilder.setConfiguration(configuration);

        return getInstance();
    }


    @NonNull
    private Context context;
    @NonNull
    private PushConfiguration configuration;
    private NetworkManager networkManager;

    /**
     * Default constructor for installation
     *
     * @param context       the application context
     * @param configuration the configuration for the manager
     */
    private PushpixlManager(@NonNull Context context, @NonNull PushConfiguration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
        networkManager = new NetworkManager(this.context);
    }

    /**
     * Get the current used configuration
     *
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
     * @param listener    a listener to handle success and error
     */
    public void updateUserPreferences(@NonNull final UserPreferences preferences, @Nullable final UserPreferencesListener listener) {
        Log.i(PushpixlManager.NP_LOG_TAG, "Updating user preferences");

        PushPixlPreferences.setUserPreferences(context, preferences);

        if (FirebaseInstanceId.getInstance() == null) {
            throw new IncorrectConfigurationException("The Firebase SDK is not configurated, check the troubleshooting section on github : https://github.com/neopixl/pushpixl-sdk-android");
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful() || task.getResult() == null) {
                            Log.i(PushpixlManager.NP_LOG_TAG, "There is no firebase token");
                            if (listener != null) {
                                listener.onUserPreferencesError(preferences, new NoTokenException("There is no firebase token, the preferences will be uploaded automaticatly if the configuration is in `autoRefresh` mode"));
                            }
                            return;
                        }

                        String token = task.getResult().getToken();

                        Log.i(PushpixlManager.NP_LOG_TAG, "The firebase token already exist");

                        networkManager.registerDevice(configuration, preferences, token, listener);
                    }
                });
    }

    /**
     * Update the user data on the Pushpixl servers based on the already saved preferences
     */
    public void reloadUserPreferences() {
        this.reloadUserPreferences(null);
    }

    /**
     * Update the user data on the Pushpixl servers based on the already saved preferences
     *
     * @param listener a listener to handle success and error
     */
    public void reloadUserPreferences(@Nullable UserPreferencesListener listener) {
        Log.i(PushpixlManager.NP_LOG_TAG, "Trying to reload user preferences");

        UserPreferences preferences = PushPixlPreferences.getUserPreferences(context);
        if (preferences == null) {
            Log.i(PushpixlManager.NP_LOG_TAG, "There is no preferences already saved");
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
     *
     * @param listener a listener to handle success and error
     */
    public void removeUserPreferences(@Nullable final UserPreferencesRemoveListener listener) {
        Log.i(PushpixlManager.NP_LOG_TAG, "Removing user preferences");

        if (FirebaseInstanceId.getInstance() == null) {
            throw new IncorrectConfigurationException("The Firebase SDK is not configurated, check the troubleshooting section on github : https://github.com/neopixl/pushpixl-sdk-android");
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful() || task.getResult() == null) {
                            Log.i(PushpixlManager.NP_LOG_TAG, "There is no firebase token");
                            if (listener != null) {
                                listener.onUserPreferencesRemoveError(new NoTokenException("There is no firebase token, the preferences will be uploaded automaticatly if the configuration is in `autoRefresh` mode"));
                            }
                            return;
                        }

                        String token = task.getResult().getToken();

                        Log.i(PushpixlManager.NP_LOG_TAG, "The firebase token already exist");

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
                });
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     *
     * @param remoteMessage the message
     */
    public void confirmReading(@NonNull RemoteMessage remoteMessage) {
        confirmReading(remoteMessage, null);
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     *
     * @param remoteMessage the message
     * @param listener      a listener to handle success and error
     */
    public void confirmReading(@NonNull RemoteMessage remoteMessage, ReadConfirmationListener listener) {
        String messageId = PushpixlData.extractData(remoteMessage, PushpixlData.KEY.ID);
        if (messageId != null) {
            confirmReading(messageId, listener);
        }
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     *
     * @param messageId the message ID
     */
    public void confirmReading(@NonNull String messageId) {
        confirmReading(messageId, null);
    }

    /**
     * Mark the current message as `read` helpfull for statistics
     *
     * @param messageId the message ID
     * @param listener  a listener to handle success and error
     */
    public void confirmReading(@NonNull final String messageId, @Nullable final ReadConfirmationListener listener) {
        Log.i(PushpixlManager.NP_LOG_TAG, "Try handle notification id : " + messageId);

        if (FirebaseInstanceId.getInstance() == null) {
            throw new IncorrectConfigurationException("The Firebase SDK is not configurated, check the troubleshooting section on github : https://github.com/neopixl/pushpixl-sdk-android");
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful() || task.getResult() == null) {
                            Log.i(PushpixlManager.NP_LOG_TAG, "There is no firebase token");
                            if (listener != null) {
                                listener.onMessageMarkedAsReadError(messageId, new NoTokenException("There is no firebase token, nothing have been sent to the server"));
                            }
                            return;
                        }

                        String token = task.getResult().getToken();


                        Log.i(PushpixlManager.NP_LOG_TAG, "handle notification id : " + messageId);
                        networkManager.confirmReading(configuration, token, messageId, listener);
                    }
                });

    }

    /**
     * Send a push notification via PushPixl, on this device
     *
     * @param message the message to display
     */
    public void pushToMySelf(@NonNull final String message, @Nullable final NotificationSendListener listener) {
        Log.i(PushpixlManager.NP_LOG_TAG, "Will launch a push to this user");

        if (FirebaseInstanceId.getInstance() == null) {
            throw new IncorrectConfigurationException("The Firebase SDK is not configurated, check the troubleshooting section on github : https://github.com/neopixl/pushpixl-sdk-android");
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful() || task.getResult() == null) {
                            Log.i(PushpixlManager.NP_LOG_TAG, "There is no firebase token");
                            if (listener != null) {
                                listener.onNotificationError(message, new NoTokenException("There is no firebase token, the preferences will be uploaded automaticatly if the configuration is in `autoRefresh` mode"));
                            }
                            return;
                        }

                        String token = task.getResult().getToken();

                        Log.i(PushpixlManager.NP_LOG_TAG, "Will launch a push to this user : " + token);
                        networkManager.pushToMySelf(configuration, token, message, listener);
                    }
                });
    }
}
