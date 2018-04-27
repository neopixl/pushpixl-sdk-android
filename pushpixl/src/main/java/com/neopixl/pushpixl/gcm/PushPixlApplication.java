package com.neopixl.pushpixl.gcm;

import android.app.Application;

import com.neopixl.pushpixl.core.util.PushPixlContext;

/**
 * PushPixlApplication
 * Used only to set Application context (reused in NotificationManager)
 * @author odemolliens
 *
 */
public class PushPixlApplication extends Application{

	@Override
	public void onCreate() {
		PushPixlContext.install(this);
		super.onCreate();
	}
}
