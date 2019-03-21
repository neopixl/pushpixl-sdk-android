package com.neopixl.pushpixl.listener;

import com.neopixl.pushpixl.exception.PushpixlException;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public interface NotificationSendListener {

    void onNotificationSent(String token, String message);

    void onNotificationError(String message, PushpixlException exception);
}
