package com.neopixl.pushpixl.network;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.neopixl.pushpixl.PushpixlManager;
import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.exception.PushNetworkException;
import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.listener.NotificationSendListener;
import com.neopixl.pushpixl.listener.ReadConfirmationListener;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.listener.UserPreferencesRemoveListener;
import com.neopixl.pushpixl.model.PushConfiguration;
import com.neopixl.pushpixl.model.QuietTime;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.pushpixl.network.model.Subscription;
import com.neopixl.pushpixl.network.util.URLBuilder;
import com.neopixl.pushpixl.util.TagsUtil;
import com.neopixl.spitfire.listener.RequestListener;
import com.neopixl.spitfire.request.BaseRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class NetworkManager {

    private RequestQueue requestQueue;

    public NetworkManager(@NonNull Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Generate the default headers for all requests
     *
     * @param configuration the {@link PushConfiguration}
     * @return an hasmap with the default headers for all requests
     */
    private Map<String, String> defaultHeaders(@NonNull final PushConfiguration configuration) {
        Map<String, String> headers = new HashMap<>();

        String credentials = configuration.getToken() + ":" + configuration.getSecret();
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", "Basic "+ encodedCredentials);

        return headers;
    }

    /**
     * Send the network request to the pushpixl instance in order to register the device
     *
     * @param configuration the {@link PushConfiguration}
     * @param preferences the {@link UserPreferences} of the user
     * @param token the firebase token
     * @param listener an listener
     */
    public void registerDevice(@NonNull final PushConfiguration configuration, @NonNull  final UserPreferences preferences, @NonNull  final String token, @Nullable final UserPreferencesListener listener) {
        String urlRegistration = URLBuilder.getInstance().getSubscriptionUrl();

        List<String> enhancedTags = new ArrayList<>();
        if (configuration.getDefaultTags() != null) {
            enhancedTags.addAll(configuration.getDefaultTags());
        }
        if (preferences.getTags() != null) {
            enhancedTags.addAll(preferences.getTags());
        }
        enhancedTags.addAll(TagsUtil.getAdditionalTags());

        boolean isInReleaseMode = !configuration.isDebug();

        QuietTime quietTime = configuration.getDefaultQuietTime();
        if (preferences.getQuietTime() != null) {
            quietTime = preferences.getQuietTime();
        }
        if (quietTime == null) {
            throw new IncorrectConfigurationException("Quiet time cannot be null, if no default are setted");
        }

        Subscription sub = new Subscription(preferences.getAlias(), token, quietTime, enhancedTags, true, false, isInReleaseMode);

        Map<String, String> headers = defaultHeaders(configuration);

        BaseRequest<Subscription> request = new BaseRequest.Builder<>(
                Request.Method.POST,
                urlRegistration,
                Subscription.class
        )
                .headers(headers)
                .json(sub)
                .listener(new RequestListener<Subscription>() {
                    @Override
                    public void onSuccess(@NonNull Request request, @NonNull NetworkResponse response, Subscription result) {
                        Log.d(PushpixlManager.NP_LOG_TAG, "device is now registered!");
                        if (listener != null) {
                            listener.onUserPreferencesUpdate(token, preferences);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Request request, NetworkResponse response, VolleyError volleyError) {
                        volleyError.printStackTrace();
                        if (listener != null) {
                            PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                            listener.onUserPreferencesError(preferences, pushpixlException);
                        }
                    }
                })
                .build();

        request.setTag("register");
        requestQueue.add(request);
    }

    /**
     * Send the network request to the pushpixl instance in order to unregister the device
     *
     * @param configuration the {@link PushConfiguration}
     * @param token the firebase token
     * @param listener an listener
     */
    public void unregisterDevice(@NonNull final PushConfiguration configuration, @NonNull final String token, @Nullable final UserPreferencesRemoveListener listener) {
        String urlUnregister = URLBuilder.getInstance().getUnsubscriptionUrl(token);

        Map<String, String> headers = defaultHeaders(configuration);

        BaseRequest<Void> request = new BaseRequest.Builder<>(
                Request.Method.DELETE,
                urlUnregister,
                Void.class
        )
                .headers(headers)
                .listener(new RequestListener<Void>() {
            @Override
            public void onSuccess(@NonNull Request request, @NonNull NetworkResponse response, Void result) {
                if (listener != null) {
                    listener.onUserPreferencesRemoved(token);
                } else {
                    Log.d(PushpixlManager.NP_LOG_TAG, "device is now registered!");
                }
            }

            @Override
            public void onFailure(@NonNull Request request, NetworkResponse response, VolleyError volleyError) {
                volleyError.printStackTrace();
                if (listener != null) {
                    PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                    listener.onUserPreferencesRemoveError(pushpixlException);
                }
            }
        })
                .build();

        request.setTag("unregister");
        requestQueue.add(request);
    }

    /**
     * Send the network request to the pushpixl instance in order to confirm that a push notification has been read
     *
     * @param configuration the {@link PushConfiguration}
     * @param token the firebase token
     * @param messageId the _nid of the push notification (internal to Pushpixl)
     * @param listener an listener
     */
    public void confirmReading(@NonNull final PushConfiguration configuration, @NonNull final String token, @NonNull final String messageId, @Nullable final ReadConfirmationListener listener) {
        String urlConfirmReading = URLBuilder.getInstance().getReadMessageUrl();

        Map<String, String> headers = defaultHeaders(configuration);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("deviceToken", token);
        parameters.put("notificationId",messageId);
        parameters.put("provider", Subscription.SUBSCRIBTION_TYPE);


        BaseRequest<Void> request = new BaseRequest.Builder<>(
                Request.Method.POST,
                urlConfirmReading,
                Void.class
        )
                .headers(headers)
                .parameters(parameters)
                .listener(new RequestListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Request request, @NonNull NetworkResponse response, Void result) {
                        if (listener != null) {
                            listener.onMessageMarkedAsReadSuccess(token, messageId);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Request request, NetworkResponse response, VolleyError volleyError) {
                        volleyError.printStackTrace();
                        if (listener != null) {
                            PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                            listener.onMessageMarkedAsReadError(messageId, pushpixlException);
                        }
                    }
                })
                .build();

        request.setTag("CONFIRM_READING");
        requestQueue.add(request);
    }

    /**
     * Send a dummy push notification to the provided token
     *
     * @param configuration the {@link PushConfiguration}
     * @param token the firebase token
     * @param message the message to send
     * @param listener an listener
     */
    public void pushToMySelf(@NonNull final PushConfiguration configuration, @NonNull final String token, @NonNull final String message, @Nullable final NotificationSendListener listener) {
        String url = URLBuilder.getInstance().getPushMySelf(token);

        Map<String, String> headers = defaultHeaders(configuration);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("messageBody", message);
        parameters.put("appKey", configuration.getToken());

        String isInProductionValue = Boolean.valueOf(!configuration.isDebug()).toString();
        parameters.put("prod", isInProductionValue);


        BaseRequest<Void> request = new BaseRequest.Builder<>(
                Request.Method.POST,
                url,
                Void.class
        )
                .headers(headers)
                .parameters(parameters)
                .listener(new RequestListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Request request, @NonNull NetworkResponse response, Void result) {
                        if (listener != null) {
                            listener.onNotificationSent(token, message);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Request request, NetworkResponse response, VolleyError volleyError) {
                        volleyError.printStackTrace();
                        if (listener != null) {
                            PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                            listener.onNotificationError(message, pushpixlException);
                        }
                    }
                })
                .build();

        request.setTag("SEND_MESSAGE");
        requestQueue.add(request);
    }
}
