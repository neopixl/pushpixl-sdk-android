package com.neopixl.pushpixl.core.util;

import android.content.Context;

import com.neopixl.logger.NPLog;

public final class PushPixlContext {
	//Singleton
	private static PushPixlContext mInstance;

	//Global
	private static Context mContext;

	public final Context getContext() {
		if(mContext==null){
			NPLog.e("Application context undefined in Application class. Please call first PushPixlContext.install(context); or subclass PushPixlApplication.");
		}

		return mContext;
	}

	// Restrict the constructor from being instantiated
	private PushPixlContext(Context context) {
		mContext = context;
	}

	public final static void install(Context context) {
		mInstance=new PushPixlContext(context);
	}

	public final static PushPixlContext getInstance(){
		if(mContext==null){
			NPLog.e("Application context undefined in Application class. Please call first PushPixlContext.install(context); or subclass PushPixlApplication.");
		}

		return mInstance;
	}

}
