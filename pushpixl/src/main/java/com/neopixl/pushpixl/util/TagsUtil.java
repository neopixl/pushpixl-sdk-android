package com.neopixl.pushpixl.util;

import android.os.Build;
import android.support.annotation.Nullable;

import com.neopixl.pushpixl.BuildConfig;

import java.util.ArrayList;
import java.util.List;

public class TagsUtil {

	private static final String PP_SDK_VERSION_CODE = "pp:sdkVersionCode";
	private static final String PP_SDK_VERSION_NAME = "pp:sdkVersionName";
	private static final String PP_DEVICE_MODEL = "pp:deviceModel";
	private static final String PP_DEVICE_BRAND = "pp:deviceBrand";
	private static final String PP_DEVICE_VERSION_NUMBER = "pp:deviceOSVersionNumber";
	
	public static ArrayList<String> getAdditionalTags() {
		String model = Build.MODEL;
		String brand = Build.MANUFACTURER;
		String releaseVersion = Build.VERSION.RELEASE;
		
		ArrayList<String> additionalTags = new ArrayList<String>();
		additionalTags.add(PP_SDK_VERSION_CODE+"="+ BuildConfig.VERSION_CODE);
		additionalTags.add(PP_SDK_VERSION_NAME+"="+ BuildConfig.VERSION_NAME);
		additionalTags.add(PP_DEVICE_MODEL+"="+model);
		additionalTags.add(PP_DEVICE_BRAND+"="+brand);
		additionalTags.add(PP_DEVICE_VERSION_NUMBER+"="+releaseVersion);
		
		return additionalTags;
	}

	public static boolean isValid(@Nullable List<String> tagList) {
		if (tagList != null && tagList.isEmpty()) {
			return false;
		}

		int numberOfTags = 0;
		if (null != tagList) {
			numberOfTags = tagList.size();
		}

		for(int i=0;i<numberOfTags;i++) {
			String tag = tagList.get(i);
			boolean isIncorrectTag = false;

			if(null!=tag) {
				isIncorrectTag = tag.split(":").length>1;
			}

			if(isIncorrectTag) {
				return false;
			}
		}
		return true;
	}
}
