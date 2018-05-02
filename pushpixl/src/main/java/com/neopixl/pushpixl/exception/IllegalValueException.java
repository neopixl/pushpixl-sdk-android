package com.neopixl.pushpixl.exception;

/**
 * Created by Yvan Moté on 08/09/2014.
 */
public class IllegalValueException extends RuntimeException {

    public IllegalValueException() {

    }

    public IllegalValueException(String detailMessage) {
        super(detailMessage);
    }
}
