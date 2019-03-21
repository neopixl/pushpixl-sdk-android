package com.neopixl.pushpixl.listener;

import com.neopixl.pushpixl.exception.PushpixlException;
import com.neopixl.pushpixl.model.PushConfiguration;
import com.neopixl.pushpixl.model.UserPreferences;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public interface UserPreferencesListener {

    void onUserPreferencesUpdate(String token, UserPreferences userPreferences);

    void onUserPreferencesError(UserPreferences userPreferences, PushpixlException exception);
}
