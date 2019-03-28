package com.neopixl.pushpixl.exception;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class IncorrectConfigurationException extends RuntimeException {

    public IncorrectConfigurationException() {
    }

    public IncorrectConfigurationException(String message) {
        super(message);
    }

    public IncorrectConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectConfigurationException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public IncorrectConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
