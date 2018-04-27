package com.neopixl.pushpixl.core.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PermissionUtil {

	/**
	 * Allows applications to open network sockets.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToInternet(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.INTERNET, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows access to the list of accounts in the Accounts Service 
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToGoogleAccounts(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.GET_ACCOUNTS, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows an app to access approximate location derived from network location 
	 * sources such as cell towers and Wi-Fi.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToCoarseLocation(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.ACCESS_COARSE_LOCATION, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows an app to access precise location from location sources such as GPS, 
	 * cell towers, and Wi-Fi.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToFineLocation(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.ACCESS_FINE_LOCATION, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows applications to access information about networks
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToNetworkState(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.ACCESS_NETWORK_STATE, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows applications to access information about Wi-Fi networks
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToWifiState(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.ACCESS_WIFI_STATE, 
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows an application to initiate a phone call without going through the Dialer 
	 * user interface for the user to confirm the call being placed.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToPhoneDialer(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.CALL_PHONE,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows an application to clear the caches of all installed applications on the device.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canClearAppCache(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.CLEAR_APP_CACHE,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/*
	/**
	 * Allows an application to clear user data
	 * @param context
	 * @return if permission is active
	 */

	/*
	public static boolean canClearUserData(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.CLEAR_APP_USER_DATA,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}
	*/

	/**
	 * Allows an application to delete cache files.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canDeleteCacheFiles(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.DELETE_CACHE_FILES,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows an application to read the user's calendar data.
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canReadCalendar(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.READ_CALENDAR,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows using PowerManager WakeLocks to keep processor from sleeping or screen from dimming
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToWakeLock(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				android.Manifest.permission.WAKE_LOCK,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows receiving push message
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToC2DMReceive(Context context)
	{
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				"com.google.android.c2dm.permission.RECEIVE",
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Allows receiving C2D message
	 * @param context
	 * @return if permission is active
	 */
	public static boolean canAccessToC2DMessage(Context context)
	{
		String str = ManifestChecker.getAppPackage(context) + ".permission.C2D_MESSAGE";
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(
				str,
				context.getPackageName());
		return (hasPerm == PackageManager.PERMISSION_GRANTED);  
	}

	/**
	 * Check if a broadcastreceiver is defined in Manifest
	 * @param receiverType
	 * @param context
	 * @return if receiver is present
	 */
	public static <Receiver extends BroadcastReceiver> boolean checkIfBroadcastReceiverIsRegisteredInManifest(Class<Receiver> receiverType,Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			ActivityInfo info = pm.getReceiverInfo(new ComponentName(context, receiverType), PackageManager.GET_RECEIVERS);
			return info.enabled;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * Check if a service is defined in Manifest
	 * @param receiverType
	 * @param context
	 * @return if receiver is present
	 */
	public static <Receiver extends BroadcastReceiver> boolean checkIfServiceIsRegisteredInManifest(Class<Receiver> receiverType,Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			ActivityInfo info = pm.getReceiverInfo(new ComponentName(context, receiverType), PackageManager.GET_SERVICES);
			return info.enabled;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

}
