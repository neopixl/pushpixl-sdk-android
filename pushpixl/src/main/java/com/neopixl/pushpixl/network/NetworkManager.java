package com.neopixl.pushpixl.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.neopixl.pushpixl.PushPixlConstant;
import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.exception.PushNetworkException;
import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.listener.UserPreferencesRemoveListener;
import com.neopixl.pushpixl.model.PushConfiguration;
import com.neopixl.pushpixl.model.QuietTime;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.pushpixl.network.model.Subscription;
import com.neopixl.pushpixl.network.request.PushPixlJsonRequest;
import com.neopixl.pushpixl.network.request.PushPixlRequest;
import com.neopixl.pushpixl.network.util.URLBuilder;
import com.neopixl.pushpixl.util.TagsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class NetworkManager {

    private RequestQueue requestQueue;

    public NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void registerDevice(PushConfiguration configuration, final UserPreferences preferences, final String token, final UserPreferencesListener listener) {
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

        String json = sub.jsonValue();
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PushPixlJsonRequest request = new PushPixlJsonRequest(Request.Method.POST, urlRegistration, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (listener != null) {
                    listener.onUserPreferencesUpdate(token, preferences);
                } else {
                    Log.d(PushPixlConstant.NP_LOG_TAG, "device is now registered!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (listener != null) {
                    PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                    listener.onUserPreferencesError(preferences, pushpixlException);
                }
            }
        });

        request.setTag("register");
        request.addUserAndPassword(configuration.getToken(), configuration.getSecret());

        requestQueue.add(request);
    }

    public void unregisterDevice(final PushConfiguration configuration, final String token, final UserPreferencesRemoveListener listener) {
        String urlUnregister = URLBuilder.getInstance().getUnsubscriptionUrl(token);

        PushPixlRequest request = new PushPixlRequest(Request.Method.DELETE, urlUnregister, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (listener != null) {
                    listener.onUserPreferencesRemoved(token);
                } else {
                    Log.d(PushPixlConstant.NP_LOG_TAG, "device is now registered!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (listener != null) {
                    PushpixlException pushpixlException = new PushNetworkException("Web request went wrong", volleyError, volleyError.networkResponse);
                    listener.onUserPreferencesRemoveError(pushpixlException);
                }
            }
        });

        request.setTag("unregister");
        request.addUserAndPassword(configuration.getToken(), configuration.getSecret());

        requestQueue.add(request);
    }
}
