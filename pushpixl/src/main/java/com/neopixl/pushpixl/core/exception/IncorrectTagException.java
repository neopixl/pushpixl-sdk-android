package com.neopixl.pushpixl.core.exception;

/**
 * Created by Yvan Moté on 08/09/2014.
 */
public class IncorrectTagException extends RuntimeException {

    public IncorrectTagException() {

    }

    public IncorrectTagException(String detailMessage) {
        super(detailMessage);
    }

}
