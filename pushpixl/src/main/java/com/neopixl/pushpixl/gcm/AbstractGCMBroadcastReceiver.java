package com.neopixl.pushpixl.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;
import com.neopixl.pushpixl.core.domain.Payload;
import com.neopixl.pushpixl.core.handler.NotificationHandler;
import com.neopixl.pushpixl.core.util.PushPixlPreferences;

public abstract class AbstractGCMBroadcastReceiver extends BroadcastReceiver {

	protected abstract void onError(Context context, String error);
	protected abstract void onRegistration(Context context, String registrationId);
	protected abstract void onUnregistration(Context context);
	protected abstract void onMessageReceived(Context context, Intent intent, Payload notification);

	@Override
	public void onReceive(Context context, Intent intent) {
		NPLog.e("{Context:"+context+" - Intent:"+intent+"};");
		NPLog.e("{ExtraIntent:"+intent.getExtras()+"};");
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			Payload notification = Payload.fromIntent(intent);
			onMessageReceived(context, intent, notification);
		} else if(intent.getAction().equals(PushPixlConstant.INTENT_NOTIF_ACTION)) {
			NotificationHandler.startComponent(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		String error = intent.getStringExtra("error");
		String unregistration = intent.getStringExtra("unregistered");
		String registration = intent.getStringExtra("registration_id");

		if (error != null) {
			onError(context, error);
		} else if (unregistration != null) {
			onUnregistration(context);
		} else if (registration != null) {
			PushPixlPreferences.setRegistrationID(registration);
			onRegistration(context, registration);
		}
	}

    protected void notify(Context context,int notificationId, Notification notification) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
