package com.neopixl.pushpixl.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class NoTokenException extends PushpixlException {

    public NoTokenException() {
    }

    public NoTokenException(String message) {
        super(message);
    }

    public NoTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTokenException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NoTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
