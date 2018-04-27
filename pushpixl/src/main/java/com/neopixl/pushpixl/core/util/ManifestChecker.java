package com.neopixl.pushpixl.core.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;

public class ManifestChecker {

	public static boolean checkManifest(Context context)
	{
		boolean isCompleted = true;

		//App Permission
		if(!PermissionUtil.canAccessToInternet(context)){
			NPLog.e("android.permission.INTERNET is not defined in AndroidManifest.xml");
			isCompleted = false;
		}

		if(!PermissionUtil.canAccessToGoogleAccounts(context)){
			NPLog.e("android.permission.GET_ACCOUNTS is not defined in AndroidManifest.xml");
			isCompleted = false;
		}

		if(!PermissionUtil.canAccessToWakeLock(context)){
			NPLog.e("android.permission.WAKE_LOCK is not defined in AndroidManifest.xml");
			isCompleted = false;
		}


		//Push permission
		if(!PermissionUtil.canAccessToC2DMessage(context)){
			NPLog.e(".C2D_MESSAGE is not defined in AndroidManifest.xml");
			isCompleted = false;
		}

		if(!PermissionUtil.canAccessToC2DMReceive(context)){
			NPLog.e("com.google.android.c2dm.permission.RECEIVE  is not defined in AndroidManifest.xml");
			isCompleted = false;
		}

		return isCompleted;
	}

	public static boolean checkReceiver(Context context,BroadcastReceiver broadcastReceiver){
		//Receiver
		if(!PermissionUtil.checkIfBroadcastReceiverIsRegisteredInManifest(broadcastReceiver.getClass(), context)){
			NPLog.e("BroadcastReceiver Receiver is not defined in AndroidManifest.xml");
			return false;
		}else{
			return true;
		}

	}

	public static boolean checkService(Context context,BroadcastReceiver broadcastReceiver){

		//Service
		if(!PermissionUtil.checkIfServiceIsRegisteredInManifest(broadcastReceiver.getClass(), context)){
			NPLog.e("BroadcastReceiver Service is not defined in AndroidManifest.xml");
			return false;
		}else{
			return true;
		}
	}

	public static String getAppPackage(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.packageName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return PushPixlConstant.NP_ERROR;
		}
	}
}
