package com.neopixl.pushpixl.exception;

/**
 * Created by Yvan Moté on 08/09/2014.
 */
public class EmptyStringException extends RuntimeException {

    public EmptyStringException() {
        super("String must not be empty");
    }


    public EmptyStringException(String detailMessage) {
        super(detailMessage);
    }
}
