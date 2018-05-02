package com.neopixl.pushpixl.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.android.volley.NetworkResponse;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushNetworkException extends PushpixlException {

    public final NetworkResponse networkResponse;

    public PushNetworkException() {
        networkResponse = null;
    }

    public PushNetworkException(NetworkResponse response) {
        networkResponse = response;
    }

    public PushNetworkException(String exceptionMessage) {
        super(exceptionMessage);
        networkResponse = null;
    }

    public PushNetworkException(String exceptionMessage, NetworkResponse response) {
        super(exceptionMessage);
        networkResponse = response;
    }

    public PushNetworkException(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public PushNetworkException(String exceptionMessage, Throwable reason, NetworkResponse response) {
        super(exceptionMessage, reason);
        networkResponse = response;
    }

    public PushNetworkException(Throwable cause) {
        super(cause);
        networkResponse = null;
    }

    public PushNetworkException(Throwable cause, NetworkResponse response) {
        super(cause);
        networkResponse = response;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PushNetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, NetworkResponse networkResponse) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.networkResponse = networkResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PushNetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.networkResponse = null;
    }


}
