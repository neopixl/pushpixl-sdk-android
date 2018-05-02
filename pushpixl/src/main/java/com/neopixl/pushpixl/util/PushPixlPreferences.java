package com.neopixl.pushpixl.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.neopixl.pushpixl.model.UserPreferences;

public class PushPixlPreferences {

	private static final String PUSHPIXL_PREF = "Gcok816HADO9lyH51gt]e5Xe}wv";
	private static final String KEY_USER_PREFS = "KEY_USER_PREFS";

	public static UserPreferences getUserPreferences(Context context){
		SharedPreferences preferences = context.getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);

		String json = preferences.getString(KEY_USER_PREFS, "");
		if (json.isEmpty()) {
			return null;
		}
		Gson gson = new Gson();
		return gson.fromJson(json, UserPreferences.class);
	}

	public static void setUserPreferences(Context context, UserPreferences value){
		SharedPreferences preferences = context.getSharedPreferences(PUSHPIXL_PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		Gson gson = new Gson();
		String json = gson.toJson(value); //
		editor.putString(KEY_USER_PREFS, json);
		editor.apply();
	}

}
