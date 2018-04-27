package com.neopixl.pushpixl.core.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.neopixl.logger.NPLog;

import java.io.IOException;

//import com.google.android.gcm.GCMRegistrar;

public class GCMUtil {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GCMUtil() {

    }

    /**
	 * Get application context
	 * @return context
	 */
	private static Context getContext()
	{
		Context ctx = PushPixlContext.getInstance().getContext();
		
		if(ctx==null){
			NPLog.e("context can't be nil");
			return null;
		}else{
			return ctx;
		}
	}

	/**
	 * Register a device on GCM (and check if Google Play Service are availables or up-to-date).
     * @param activity the current activity from where you want to register and display alert dialogs for Google Play Services
     * @param listener the listener used to retrieve the registration id asynchronously
	 */
	public static void register(Activity activity, final GCMUtilRegistrationListener listener)
	{

        boolean isValidDevice = true;

        if(null!=activity) {
            isValidDevice = checkPlayServices(activity);
        }

        if(!isValidDevice) {
            NPLog.e("Device is not compatible.");
            return;
        }

		final Context context = getContext();

		if(context==null){
			NPLog.e("context==null");
			return;
		}

        new AsyncTask<Void,Integer,String>() {

            @Override
            protected String doInBackground(Void... voids) {

                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

                String registerId = "";

                try {
                    registerId = gcm.register(PushPixlManifest.getSenderID(context));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(registerId.isEmpty()) {
                    NPLog.i("registerId not available");

                    return "";
                }

                return registerId;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(null!=listener) {
                    listener.didReceiveRegistrationId(result);
                }
            }
        }.execute();
	}

    /**
     * Register a device on GCM (without performing any check about Google Play Services).
     * @param listener the listener used to retrieve the registration id asynchronously
     */
    public static void register(GCMUtilRegistrationListener listener) {
        register(null,listener);
    }

	/**
	 * Unregister a device on GCM
	 */
	public static void unregister()
	{
		Context context = getContext();

		if(context==null){
			NPLog.e("Context can't be nil");
			return;
		}

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        try {
            gcm.unregister();
        } catch (IOException e) {
            e.printStackTrace();
            NPLog.e("Error: Can't unregister");
        }
	}

	/**
	 * Check if Google Play services are available and or up-to-date.
     * @param activity the current activity in which you want to display the alert dialog for Google Play Services
	 */

    public static boolean checkPlayServices(Activity activity) {

        Context context = getContext();

        if(context==null){
            NPLog.e("Context can't be nil");
            return false;
        }

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                NPLog.i("This device is not supported.");
            }
            return false;
        }
        return true;
    }
}
