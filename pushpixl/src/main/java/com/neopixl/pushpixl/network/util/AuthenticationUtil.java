package com.neopixl.pushpixl.network.util;

import android.util.Base64;
import android.util.Log;

import com.neopixl.pushpixl.PushPixlConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yvan Moté on 26/10/2014.
 */
public class AuthenticationUtil {
    public static Map<String, String> getAuthenticationHeaders(String user, String password) {
        Map<String, String> currentHeaders = new HashMap<String, String>();

        String credentials = user + ":" + password;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        currentHeaders.put("Authorization", "Basic " + encodedCredentials);

        Log.d(PushPixlConstant.NP_LOG_TAG, "credentials : "+encodedCredentials);

        return currentHeaders;
    }
}
