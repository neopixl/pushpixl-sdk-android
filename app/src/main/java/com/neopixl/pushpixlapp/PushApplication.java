package com.neopixl.pushpixlapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.neopixl.pushpixl.PushpixlManager;
import com.neopixl.pushpixl.model.PushConfiguration;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushApplication extends Application {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationUtil.init(this);

        // ***
        // ADDED FOR LIBRARY
        PushConfiguration configuration = new PushConfiguration(
                "500e73a3-9dc8-4e92-9cb9-dd14e815e1e3",
                "ca2b64ac-991b-40fa-80f3-16be05e53461"
                , "neopixl")
                .hostname("pushpixl.lan")
                .debug(BuildConfig.DEBUG)
                .autoRefresh(true)
                .useNotSecureHttp(true)
                .askBatteryOptimization(true);

        PushpixlManager.install(this, configuration);
        // END
        // ***
    }
}
