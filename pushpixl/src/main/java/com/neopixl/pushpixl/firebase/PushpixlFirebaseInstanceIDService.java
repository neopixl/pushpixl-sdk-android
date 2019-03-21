package com.neopixl.pushpixl.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.neopixl.pushpixl.PushPixlConstant;
import com.neopixl.pushpixl.PushpixlManager;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlFirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.i(PushPixlConstant.NP_LOG_TAG, "New token received from firebase");

        if (PushpixlManager.getInstance().getConfiguration().isAutoRefresh()) {
            PushpixlManager.getInstance().reloadUserPreferences();
        }
    }
}
