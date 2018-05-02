package com.neopixl.pushpixl.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class NoPreferencesException extends PushpixlException {

    public NoPreferencesException() {
    }

    public NoPreferencesException(String message) {
        super(message);
    }

    public NoPreferencesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPreferencesException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NoPreferencesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
