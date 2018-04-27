package com.neopixl.pushpixl.core.handler;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.NotificationManager;
import com.neopixl.pushpixl.core.RequestListener;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;
import com.neopixl.pushpixl.core.domain.Payload;

public class NotificationHandler {
	private static final String EXTRA_TYPE = "extra_type";
	private static final String EXTRA_CLASS_ACTIVITY = "extra_class";
	private static final String EXTRA_CLASS_SERVICE = "extra_service";
	private static final String EXTRA_NOTIFICATION = "extra_notification";
		
	private static final int TYPE_ACTIVITY = 101;
	private static final int TYPE_SERVICE = 102;
	private static final int TYPE_OTHER = 103;

	public static Payload handleIntent(Intent intent,RequestListener nL) {
		
		if(intent==null) {
			NPLog.i("can't handle null intent.");
			return null;
		}
		
		Payload notification = intent.getParcelableExtra(EXTRA_NOTIFICATION);
		
		if(notification!=null) {
			NPLog.i("handle notification id : "+notification.getId());
			NotificationManager.confirmReading(notification.getId(), nL);
		}
		else {
			NPLog.i("don't need to handle notification");
		}
		
		return notification;
	}
	
	public static PendingIntent newPendingIntent(Context context, int pendingFlags, int intentFlags, Class<?> classComponent, Payload notification) {
		int requestCode = 0;
		
		Intent intent = getIntentForComponent(context, intentFlags, classComponent, notification);

		PendingIntent pendingIntent = null;
		
		if(Activity.class.isAssignableFrom(classComponent)) {
            NPLog.e("pending activity intent");

            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, pendingFlags);
		}
		else if(Service.class.isAssignableFrom(classComponent)) {
            NPLog.e("pending service intent");

            pendingIntent = PendingIntent.getService(context, requestCode, intent, pendingFlags);
		}
		else {
			NPLog.e("pending intent => null");
		}
		
		return pendingIntent;
	}
	
	private static Intent getIntentForComponent(Context context, int intentFlags, Class<?> classComponent, Payload notification) {
		Intent intent = new Intent(context, classComponent);
		intent.putExtra(EXTRA_NOTIFICATION, notification);
		intent.setFlags(intentFlags);
		
		return intent;
	}
	
	@SuppressWarnings("unchecked")
	public static void startComponent(Context context, Intent intent) {
		int currentType = intent.getIntExtra(EXTRA_TYPE, TYPE_OTHER);
		
		switch (currentType) {
		case TYPE_ACTIVITY: {
			ComponentName componentName = intent.getParcelableExtra(EXTRA_CLASS_ACTIVITY);
			
			Class<Activity> activityClass;
			try {
				activityClass = (Class<Activity>) Class.forName(componentName.getClassName());
				Intent newIntent = new Intent(context,activityClass);
				context.startActivity(newIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			
		}
			break;
		case TYPE_SERVICE: {
			ComponentName componentName = intent.getParcelableExtra(EXTRA_CLASS_SERVICE);
			
			Class<Service> serviceClass;
			try {
				serviceClass = (Class<Service>) Class.forName(componentName.getClassName());
				Intent newIntent = new Intent(context,serviceClass);
				context.startService(newIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			context.startService(intent);
		}
			break;
		default:
			break;
		}
	}
	
	public static void fillIntent(Intent intent) {
				
		NPLog.i("action before : "+intent.getAction());
		intent.setAction(PushPixlConstant.INTENT_NOTIF_ACTION);
		NPLog.i("action after : "+intent.getAction());

	}
}
