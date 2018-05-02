package com.neopixl.pushpixl.listener;

import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.model.UserPreferences;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public interface UserPreferencesRemoveListener {

    void onUserPreferencesRemoved(String token);

    void onUserPreferencesRemoveError(PushpixlException exception);
}
