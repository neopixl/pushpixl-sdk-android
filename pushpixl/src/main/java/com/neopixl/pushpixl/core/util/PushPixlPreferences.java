package com.neopixl.pushpixl.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;

public class PushPixlPreferences {

	private static final String PUSHPIXL_PREF = "Gcok816HADO9lyH51gt]e5Xe}wv";
	private static final String REGISTRATION_ID = "5#7dsjnY{!7W!46_0:8geI+30h:ZC*p)1uEShCX9|=lhSfU}u~V0h$8o5";
	
	private static final String REGISTRATION_ID_KEY = "REGISTRATION_ID";
	

	/**
	 * Used only for Demo app !
	 */
	private static final String PUSHPIXL_APP_TOKEN = "LFn7w86D8edfCDAGFknRZK922dSFnsBg7QShWx9rGYhBnbWqE4";
	private static final String PUSHPIXL_APP_SECRET = "UELCxSUuFrXX6xfg6yrd2wYuAm6exWT4mrjFyMHpDGtTFG5Rty";
	private static final String PUSHPIXL_GCM_SENDER_ID = "UmgM7H7BtVMLU6aAgx4Eg9nnQETxEr4auWHtubyHRqHU28x2DQ";
	private static final String PUSHPIXL_APP_TENANT = "Hs95q2R8PX8nH46f4XFxg67t6g6ccEyyvVSLPJuXgp46TybYRW";
	
	
	private static final String PUSHPIXL_APP_TOKEN_KEY = "APP_TOKEN";
	private static final String PUSHPIXL_APP_SECRET_KEY = "APP_SECRET";
	private static final String PUSHPIXL_APP_TENANT_KEY = "APP_TENANT";
	private static final String PUSHPIXL_GCM_SENDER_ID_KEY = "GCM_SENDER_KEY";
	

	/**
	 * Get application context
	 * @return	context
	 */
	private static Context getContext()
	{		
		return PushPixlContext.getInstance().getContext();
	}

	/**
	 * Get application package
	 * @return	String with package for example: "com.neopixl.mainpackage"
	 */
	private static String getPackageName()
	{
		return DeviceUtil.getAppPackage(getContext());
	}

	/**
	 * Get registration from GCM
	 * @return	String with GCM identifier
	 */
	public static String getRegistrationID(){
		SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
		return preferences.getString(REGISTRATION_ID_KEY,PushPixlConstant.NP_ERROR);
	}

	/**
	 * Set the registration ID
	 * @param value
	 */
	public static void setRegistrationID(String value){
		SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);  
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(REGISTRATION_ID_KEY, value);
		editor.commit();
	}
	

	/**
	 * Return Sender ID (used only with scheme url)
	 * Used only for demo App !
	 * @return 
	 */
	public static String getSenderID(){
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
			return preferences.getString(PUSHPIXL_GCM_SENDER_ID_KEY,PushPixlConstant.NP_ERROR);
		}else{
			NPLog.i("This method is only available with the demo app");
			return PushPixlConstant.NP_ERROR;
		}
	}

	/**
	 * Set the sender ID
	 * @param value
	 */
	public static void setSenderID(String value){
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);  
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(PUSHPIXL_GCM_SENDER_ID_KEY, value);
			editor.commit();
		}else{
			NPLog.i("This method is only available with the demo app");
		}
	}
	
	/**
	 * Return Application Token (used only with scheme url)
	 * Used only for demo App !
	 * @return	String
	 */
	public static String getApplicationToken() {
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
			return preferences.getString(PUSHPIXL_APP_TOKEN_KEY,PushPixlConstant.NP_ERROR);
		}else{
			NPLog.i("This method is only available with the demo app");
			return PushPixlConstant.NP_ERROR;
		}
	}
	

	/**
	 * Set App Token Developpement (used only with scheme url)
	 * Used only for demo App !
	 * @param value
	 */
	public static void setApplicationDemoToken(String value){

		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);  
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(PUSHPIXL_APP_TOKEN_KEY, value);
			editor.commit();
		}else{
			NPLog.i("This method is only available with the demo app");
		}
	}

	

		
	/**
	 * Return Application secret key  (used only with scheme url)
	 * Used only for demo App !
	 * @return	String
	 */

	public static String getApplicationSecret(){
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
			return preferences.getString(PUSHPIXL_APP_SECRET_KEY,PushPixlConstant.NP_ERROR);
		}else{
			NPLog.i("This method is only available with the demo app");
			return PushPixlConstant.NP_ERROR;
		}

	}

	/**
	 * Set App secret key (used only with scheme url)
	 * Used only for demo App !
	 * @param value
	 */
	public static void setApplicationDemoSecret(String value){
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);  
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(PUSHPIXL_APP_SECRET_KEY, value);
			editor.commit();
		}else{
			NPLog.i("This method is only available with the demo app");
		}
	}
	
	/**
	 * Return App Tenant (used only with scheme url)
	 * Used only for demo App !
	 * @return 
	 */
	public static String getApplicationTenant(){
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
			return preferences.getString(PUSHPIXL_APP_TENANT_KEY,PushPixlConstant.NP_ERROR);
		}else{
			NPLog.i("This method is only available with the demo app");
			return PushPixlConstant.NP_ERROR;
		}
	}

	/**
	 * Set App Tenant (used only with scheme url)
	 * Used only for demo App !
	 * @param value 
	 */
	public static void setApplicationDemoTenant(String value){
		
		if(getPackageName().equals(PushPixlConstant.DEMO_APPLICATION_PACKAGE)){
			SharedPreferences preferences = getContext().getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);  
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(PUSHPIXL_APP_TENANT_KEY, value);
			editor.commit();
		}else{
			NPLog.i("This method is only available with the demo app");
		}
	}
}
