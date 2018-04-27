package com.neopixl.pushpixl.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import com.neopixl.pushpixl.core.conf.PushPixlConstant;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;

@SuppressLint("DefaultLocale")
public class DeviceUtil {

	
	/**
	 * Get device model
	 * @return	String
	 */
	public static String getDeviceModel()
	{
		return android.os.Build.MODEL;
	}

	/**
	 * Get Device version
	 * For example: 3.1 (Honeycomb)
	 * @return	String
	 */
	public static String getDeviceVersion()
	{
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * Get Device version
	 * For example: 3.1 (Honeycomb)
	 * @return	String
	 */
	public static int getIntDeviceVersion()
	{
		return android.os.Build.VERSION.SDK_INT;
	}
	
	/**
	 * Get package name from manifest
	 * @param context
	 * @return if permission is active
	 */
	public static String getAppPackage(Context context)
	{
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.packageName; 
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return PushPixlConstant.NP_ERROR;
		}
	}
	
	public enum DevicePower {
		HIGH_MEMORY_DEVICE,//>80mo
		MIDDLE_MEMORY_DEVICE,//>40mo
		LOW_MEMORY_DEVICE
	}

	/**
	 * Return device power
	 * HIGH_MEMORY_DEVICE>80mo
	 * MIDDLE_MEMORY_DEVICE>40mo
	 * LOW_MEMORY_DEVICE<40mo
	 * @return DevicePower type
	 */
	public static DevicePower getDevicePower()
	{
		long value = DeviceUtil.convertBytesInMb(DeviceUtil.getMaxSizeHeap());

		if(value >80){
			return DevicePower.HIGH_MEMORY_DEVICE;
		}else if(value >40){
			return DevicePower.MIDDLE_MEMORY_DEVICE;
		}else{
			return DevicePower.LOW_MEMORY_DEVICE;
		}
	}

	/**
	 * Get device UUID
	 * @param context
	 * @return	String
	 */
	public static String getDeviceUUID(Context context)
	{
		final android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) ((ContextWrapper) context).getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return deviceUuid.toString();
	}

    /**
     * Get generated alias for the current device (concatenation of the brand, model and the name of the device)
     * @return	String
     */
	public static String getDeviceName() {
		String alias = Build.BRAND+"_"+Build.MODEL+"_"+Build.DEVICE;
		return alias;
	}

	/**
	 * Get information aboug CPU
	 * @return String
	 */
	public static String readCPUinfo()
	{
		ProcessBuilder cmd;
		String result="";

		try{
			String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
			cmd = new ProcessBuilder(args);

			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while(in.read(re) != -1){
				System.out.println(new String(re));
				result = result + new String(re);
			}
			in.close();
		} catch(IOException ex){
			ex.printStackTrace();
		}
		return result;
	}



	/**
	 * Show logs about memory and CPU Usage
	 */
	public static void showMemoryAndCpuUsage(){
		Log.e("DeviceUtil","CPU Usage:"+DeviceUtil.readUsage());
		Log.e("DeviceUtil","Free memory:"+DeviceUtil.getFreeMemorySize());
		Log.e("DeviceUtil","Total memory:"+DeviceUtil.getTotalMemorySize());
		Log.e("DeviceUtil","Used memory:"+DeviceUtil.getUsedMemorySize());
	}

	/**
	 * Get all used memory size
	 * @return	long
	 */
	public static long getUsedMemorySize() {

		long freeSize = 0L;
		long totalSize = 0L;
		long usedSize = -1L;
		try {
			Runtime info = Runtime.getRuntime();
			freeSize = info.freeMemory();
			totalSize = info.totalMemory();
			usedSize = totalSize - freeSize;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usedSize;

	}

	/**
	 * Get total memory on a device
	 * @return	long
	 */
	public static long getTotalMemorySize() {

		long totalSize = 0L;
		try {
			Runtime info = Runtime.getRuntime();
			totalSize = info.totalMemory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalSize;
	}

	/**
	 * Get free memory on a device
	 * @return	long
	 */
	public static long getFreeMemorySize() {

		long freeSize = 0L;
		try {
			Runtime info = Runtime.getRuntime();
			freeSize = info.freeMemory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return freeSize;

	}

	/**
	 * Easy converter bytes to megabytes
	 * @param bytes value
	 * @return long megabytes
	 */
	public static long convertBytesInMb(long bytes)
	{
		long MEGABYTE = 1024L * 1024L;
		long b = bytes / MEGABYTE;
		return b;
	}

	/**
	 * Get CPU usage
	 * @return	float
	 */
	public static float readUsage() {
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
					+ Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			try {
				Thread.sleep(360);
			} catch (Exception e) {}

			reader.seek(0);
			load = reader.readLine();
			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
					+ Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return 0;
	} 

	/**
	 * Get App version from manifest
	 * @param context
	 * @return	String
	 */
	public static String getAppVersion(Context context){

		PackageInfo pInfo;
		try {
			pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * Get device language (only two first character: "fr_be" will be "fr")
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getDeviceLanguage(){
		return Locale.getDefault().getDisplayLanguage().toLowerCase().substring(0, 2);
	}

	/**
	 * Get Max size heap
	 * @return	long
	 */
	public static long getMaxSizeHeap()
	{
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * Force grow heap with parameter
	 * Warning, only run on project when target is < API 10
	 * @param megaGrow
	 */
	public static void forceGrowHeapSizeTo(long megaGrow)
	{
		Log.e("GrowerMode","MaxSize " + convertBytesInMb(getMaxSizeHeap()) + " Change to: "+megaGrow);		

		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) <= 9) {
			try {
				@SuppressWarnings("unchecked")
				Class<Object> ClassVMRuntime = (Class<Object>) Class.forName("dalvik.system.VMRuntime");
				Method methodGetRuntime = ClassVMRuntime.getDeclaredMethod("getRuntime", null);
				Object runtime = methodGetRuntime.invoke(ClassVMRuntime, null);

				Method methodSetMinimumHeapSize = runtime.getClass().getDeclaredMethod("setMinimumHeapSize", long.class);
				methodSetMinimumHeapSize.invoke(runtime, Long.valueOf(megaGrow * 1024 * 1024));
			}
			catch(Exception e) {
				Log.e("GrowerMode", "impossible to grown app memory", e);
			}
		}
	}

	/**
	 * Force grow heap to the max (by default, 30 mb are setted)
	 * Warning, only run on project when target is < API 10
	 */
	public static void forceGrowHeapSizeMax()
	{
		int maxValue = 30;

		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) <= 9) {
			if(convertBytesInMb(getMaxSizeHeap())>maxValue){
				Log.e("GrowerMode","MaxSize " + convertBytesInMb(getMaxSizeHeap()) + " Change to: "+maxValue);
				DeviceUtil.forceGrowHeapSizeTo(maxValue * 1024 * 1024);

			}else{
				Log.e("GrowerMode","MaxSize " + convertBytesInMb(getMaxSizeHeap()) + " Change to: "+convertBytesInMb(getMaxSizeHeap()));
				DeviceUtil.forceGrowHeapSizeTo(convertBytesInMb(getMaxSizeHeap()) * 1024 * 1024);
			}
		}
	}

	/**
	 * Force system to call Garbage Collector
	 */
	public static void forceGCcleaning()
	{
		/*
		 * Not really force, but can help
		 */
		Runtime.getRuntime().gc();
	}

}
