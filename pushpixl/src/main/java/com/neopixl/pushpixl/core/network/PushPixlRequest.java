package com.neopixl.pushpixl.core.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yvan Moté on 26/10/2014.
 */
public class PushPixlRequest extends StringRequest {


    private String mUser;
    private String mPassword;

    private HashMap<String, String> mMapParams;

    public PushPixlRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

        mMapParams = new HashMap<String, String>();
    }

    public PushPixlRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);

        mMapParams = new HashMap<String, String>();
    }

    public void addUserAndPassword(String user, String password) {
        this.mUser = user;
        this.mPassword = password;
    }

    public void addParameter(String key, String value) {
        mMapParams.put(key,value);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMapParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return AuthenticationUtil.getAuthenticationHeaders(mUser,mPassword);
    }
}
