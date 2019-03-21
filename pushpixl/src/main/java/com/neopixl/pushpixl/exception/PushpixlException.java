package com.neopixl.pushpixl.exception;

import android.os.Build;
import androidx.annotation.RequiresApi;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
abstract public class PushpixlException extends Exception {

    public PushpixlException() {
    }

    public PushpixlException(String message) {
        super(message);
    }

    public PushpixlException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushpixlException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public PushpixlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
