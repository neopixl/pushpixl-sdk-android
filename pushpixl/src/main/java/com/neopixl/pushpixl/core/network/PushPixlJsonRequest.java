package com.neopixl.pushpixl.core.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * Created by Yvan Moté on 25/10/2014.
 * based on http://yakivmospan.wordpress.com/2014/04/04/volley-authorization/
 */
public class PushPixlJsonRequest extends JsonObjectRequest {

    private String mUser;
    private String mPassword;

    private String mContentType;


    private String currentCookie;

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    public PushPixlJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public PushPixlJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);



        Cache.Entry currentEntry = getCacheEntry();

        if(null!=currentEntry) {
            currentEntry.serverDate = new Date().getTime();
        }
        else {
            Cache.Entry entry = new Cache.Entry();
            entry.serverDate = new Date().getTime();

            setCacheEntry(entry);
        }
    }

    public void addUserAndPassword(String user, String password) {
        this.mUser = user;
        this.mPassword = password;
    }

    /*
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        this.checkSessionCookie(response.headers);

        NPLog.d("parseNetworkResponse response headers"+response.headers);

        return super.parseNetworkResponse(response);
    }
    */

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return AuthenticationUtil.getAuthenticationHeaders(mUser,mPassword);
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }

    @Override
    public String getBodyContentType() {
        if(mContentType==null || mContentType.length()==0) {
            return super.getBodyContentType();
        }

        return mContentType;
    }


    public final void checkSessionCookie(Map<String, String> headers) {


        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);

            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];


                /*
                Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
                */
            }
        }
    }
}
