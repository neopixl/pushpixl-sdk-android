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
                "50465c62-a3e5-4bfb-9aa6-528395d3800e",
                "208b23a5-3aed-49fe-b999-f2ece02656dc"
                , "neopixl")
                .debug(BuildConfig.DEBUG)
                .autoRefresh(true)
                .askBatteryOptimization(true);

        PushpixlManager.install(this, configuration);
        // END
        // ***
    }
}
