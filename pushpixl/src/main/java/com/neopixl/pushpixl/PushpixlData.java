package com.neopixl.pushpixl;

import com.google.firebase.messaging.RemoteMessage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;


public abstract class PushpixlData {

	@StringDef({
			Key.ID,
			Key.MESSAGE
	})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Key {
		/**
		 * This represent the internal identifier of the message in the Pushpixl server instance
		 */
		String ID = "_nid";

		/**
		 * This represent the message to display to the user
		 */
		String MESSAGE = "body";
	}

	/**
	 * Extract a specific data from a know {@link Key}
	 *
	 * @param remoteMessage the firebase remote message
	 * @param key The {@link Key} you want the value back
	 * @return the value of the key, or null if not found
	 */
	@Nullable
	public static String extractData(@NonNull final RemoteMessage remoteMessage, @NonNull @Key final String key) {
		Map<String, String> data = remoteMessage.getData();
		String valueToreturn = null;
		if (data != null) {
			String valueInData = data.get(key);
			if (valueInData != null) {
				valueToreturn = valueInData;
			}
		}
		return valueToreturn;
	}

}
