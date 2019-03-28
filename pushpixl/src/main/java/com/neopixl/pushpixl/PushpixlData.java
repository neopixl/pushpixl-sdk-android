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
			KEY.ID,
			KEY.MESSAGE
	})
	@Retention(RetentionPolicy.SOURCE)
	@interface KEY {
		String ID = "_nid";
		String MESSAGE = "body";
	}

	/**
	 * Extract a specific data from a know {@link KEY}
	 *
	 * @param remoteMessage the firebase remote message
	 * @param key The {@link KEY} you want the value back
	 * @return the value of the key, or null if not found
	 */
	@Nullable
	public static String extractData(@NonNull final RemoteMessage remoteMessage, @NonNull @KEY final String key) {
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
