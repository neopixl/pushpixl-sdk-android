package com.neopixl.pushpixl.core.network;

import android.util.Base64;

import com.neopixl.logger.NPLog;

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

        NPLog.d("credentials : "+encodedCredentials);

        return currentHeaders;
    }
}
