package com.neopixl.pushpixl.core.util;

import android.os.Build;

import java.util.ArrayList;

public class TagsUtil {

	private static final String PP_DEVICE_MODEL = "pp:deviceModel";
	private static final String PP_DEVICE_BRAND = "pp:deviceBrand";
	private static final String PP_DEVICE_VERSION_NUMBER = "pp:deviceOSVersionNumber";
	
	public static ArrayList<String> getAdditionalTags() {
		String model = Build.MODEL;
		String brand = Build.MANUFACTURER;
		String releaseVersion = Build.VERSION.RELEASE;
		
		ArrayList<String> additionalTags = new ArrayList<String>();
		additionalTags.add(PP_DEVICE_MODEL+"="+model);
		additionalTags.add(PP_DEVICE_BRAND+"="+brand);
		additionalTags.add(PP_DEVICE_VERSION_NUMBER+"="+releaseVersion);
		
		return additionalTags;
	}
}
