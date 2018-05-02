package com.neopixl.pushpixl.exception;

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

    public IncorrectConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
