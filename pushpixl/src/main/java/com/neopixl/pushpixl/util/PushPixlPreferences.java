package com.neopixl.pushpixl.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.VolleyLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.neopixl.pushpixl.BuildConfig;
import com.neopixl.pushpixl.PushPixlConstant;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.spitfire.SpitfireManager;

import java.io.UnsupportedEncodingException;

public class PushPixlPreferences {

    private static final String PUSHPIXL_PREF = BuildConfig.APPLICATION_ID;
    private static final String KEY_USER_PREFS = "KEY_USER_PREFS";

    public static UserPreferences getUserPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);

        String json = preferences.getString(KEY_USER_PREFS, "");
        if (json.isEmpty()) {
            return null;
        }

        UserPreferences returnData = null;
        try {
            returnData = SpitfireManager.getObjectMapper().readValue(json.getBytes(PushPixlConstant.CHARSET_ENCODING), UserPreferences.class);
        } catch (Exception e) {
            VolleyLog.e(e, "An error occurred while parsing network response:");
        }
        return returnData;
    }

    public static void setUserPreferences(Context context, UserPreferences value) {
        SharedPreferences preferences = context.getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (value != null) {
            byte ptext[];
            try {
                ptext = SpitfireManager.getObjectMapper().writeValueAsBytes(value);
                String json = new String(ptext, PushPixlConstant.CHARSET_ENCODING);
                editor.putString(KEY_USER_PREFS, json);
            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            editor.remove(KEY_USER_PREFS);
        }
        editor.apply();
    }

}
