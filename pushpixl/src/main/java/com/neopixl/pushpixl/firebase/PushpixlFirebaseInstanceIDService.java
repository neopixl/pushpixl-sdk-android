package com.neopixl.pushpixl.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.neopixl.pushpixl.PushPixlConstant;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(PushPixlConstant.NP_LOG_TAG, "New token received from firebase");
    }
}
