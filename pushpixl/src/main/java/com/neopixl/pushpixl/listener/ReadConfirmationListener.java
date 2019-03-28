package com.neopixl.pushpixl.listener;

import com.neopixl.pushpixl.exception.PushpixlException;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public interface ReadConfirmationListener {

    void onMessageMarkedAsReadSuccess(String token, String messageId);

    void onMessageMarkedAsReadError(String messageId, PushpixlException exception);
}
