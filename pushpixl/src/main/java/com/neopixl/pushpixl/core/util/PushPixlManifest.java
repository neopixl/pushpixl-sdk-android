package com.neopixl.pushpixl.core.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;


public class PushPixlManifest {

	private static String PUSHPIXL_APP_SERVER_USE_HTTPS = "PUSHPIXL_APP_SERVER_USE_HTTPS";
	private static String PUSHPIXL_APP_SERVER_DOMAIN = "PUSHPIXL_APP_SERVER_DOMAIN";
	private static String PUSHPIXL_GCM_SENDER_ID = "PUSHPIXL_GCM_SENDER_ID";
	private static String PUSHPIXL_APP_TENANT = "PUSHPIXL_APP_TENANT";
	private static String PUSHPIXL_APP_TOKEN = "PUSHPIXL_APP_TOKEN";
	private static String PUSHPIXL_APP_SECRET = "PUSHPIXL_APP_SECRET";
	private static String PUSHPIXL_APP_RELEASE_MODE = "PUSHPIXL_APP_RELEASE_MODE";

	/**
	 * Return boolean if is in release mode or not from AndroidManifest file (Default true)
	 * @param context
	 * @return boolean value
	 */
	public static boolean isInReleaseMode(Context context)
	{
        if(context==null){
			NPLog.e("Context can't be nil");
			return false;
		}
		boolean isInReleaseMode = true;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            isInReleaseMode = ai.metaData.getBoolean(PUSHPIXL_APP_RELEASE_MODE, true);	// true by default
        } catch (Exception e) {
            NPLog.e("Can't found PUSHPIXL_APP_RELEASE_MODE meta-data in manifest");
            NPLog.e("Error log:\n"+e.getStackTrace());
        }
        return isInReleaseMode;
	}

	/**
	 * Return boolean if the application should use or not https from AndroidManifest file
	 * @param context
	 * @return boolean value
	 */

	public static boolean isApplicationusingHTTPS(Context context) {

		if(context==null){
			NPLog.e("Context can't be nil");
			return false;
		}
		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return ai.metaData.getBoolean(PUSHPIXL_APP_SERVER_USE_HTTPS);

		} catch (Exception e) {
			NPLog.e("Can't found PUSHPIXL_APP_RELEASE_MODE meta-data in manifest");
			NPLog.e("Error log:\n"+e.getStackTrace());
			return false;
		}
	}

	/**
	 * Return Application server domain from AndroidManifest file
	 * @param context
	 * @return token
	 */

	public static String getApplicationServerDomain(Context context) {

		if(context==null){
			NPLog.e("Context can't be nil");
			return null;
		}
		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return ai.metaData.getString(PUSHPIXL_APP_SERVER_DOMAIN);

		} catch (Exception e) {
			NPLog.e("Can't found PUSHPIXL_APP_RELEASE_MODE meta-data in manifest");
			NPLog.e("Error log:\n"+e.getStackTrace());
			return null;
		}
	}

	/**
	 * Return Token from AndroidManifest file
	 * @param context
	 * @return token
	 */
	public static String getToken(Context context)
	{
		String value = PushPixlPreferences.getApplicationToken();

		if(!value.equals(PushPixlConstant.NP_ERROR)){
			return value;
		}else{
			return PushPixlManifest.getApplicationToken(context);
		}
	}

	/**
	 * Return Token from AndroidManifest file
	 * @param context
	 * @return String	secret key
	 */
	public static String getSecretKey(Context context)
	{
			String value = PushPixlPreferences.getApplicationSecret();

			if(!value.equals(PushPixlConstant.NP_ERROR)){
				return value;
			}else{
				return PushPixlManifest.getApplicationSecretKey(context);
			}
	}

	/**
	 * Return the application token from AndroidManifest file
	 * @param context
	 * @return String application token
	 */

	private static String getApplicationToken(Context context) {
		if(context==null){
			NPLog.e("Context can't be nil");
			return PushPixlConstant.NP_ERROR;
		}

		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return ai.metaData.getString(PUSHPIXL_APP_TOKEN).replace("#", "");

		} catch (Exception e) {
			NPLog.e("Can't found PUSHPIXL_APP_TOKEN meta-data in manifest");
			NPLog.e("Error log:\n"+e.getStackTrace());
			return PushPixlConstant.NP_ERROR;
		}
	}

	/**
	 * Return SenderID from AndroidManifest file
	 * @param context
	 * @return String	sender ID
	 */
	public static String getSenderID(Context context)
	{
		if(context==null){
			NPLog.e("Context can't be nil");
			return PushPixlConstant.NP_ERROR;
		}

		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return ai.metaData.getString(PUSHPIXL_GCM_SENDER_ID).replace("#", "");

		} catch (Exception e) {
			NPLog.e("Can't found PUSHPIXL_GCM_SENDER_ID meta-data in manifest");
			NPLog.e("Error log:\n"+e.getStackTrace());
			return PushPixlConstant.NP_ERROR;
		}
	}

	/**
	 * Return TenantID from AndroidManifest file
	 * @param context
	 * @return String	tenant ID
	 */
	public static String getTenantID(Context context)
	{
		if(context==null){
			NPLog.e("Context can't be nil");
			return PushPixlConstant.NP_ERROR;
		}

		String value = PushPixlPreferences.getApplicationTenant();

		if(!value.equals(PushPixlConstant.NP_ERROR)){
			return value;
		}else{
			ApplicationInfo ai;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				return ai.metaData.getString(PUSHPIXL_APP_TENANT).replace("#", "");

			} catch (Exception e) {
				NPLog.e("Can't found PUSHPIXL_APP_TENANT meta-data in manifest");
				return "";
			}
		}
	}

	/**
	 * Return development secret key from AndroidManifest file
	 * @param context
	 * @return string	distribution token
	 */
	private static String getApplicationSecretKey(Context context)
	{
		if(context==null){
			NPLog.e("Context can't be nil");
			return PushPixlConstant.NP_ERROR;
		}

		ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return ai.metaData.getString(PUSHPIXL_APP_SECRET).replace("#", "");

		} catch (Exception e) {
			NPLog.e("Can't found PUSHPIXL_APP_SECRET_DEVELOPMENT meta-data in manifest");
			NPLog.e("Error log:\n"+e.getStackTrace());
			return PushPixlConstant.NP_ERROR;
		}
	}

}
