package com.neopixl.pushpixl.core.domain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.neopixl.logger.NPLog;

public class Payload implements Parcelable {

	private String mId;
	private String mAlert;

    private Bundle mExtras;

    private static final String nidKey = "_nid";
    private static final String bodyKey = "body";

	public Payload() {
		super();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

        if(null==mId) {
            dest.writeString("");
        }
        else {
            dest.writeString(mId.toString());
        }

        if(null==mAlert) {
            dest.writeString("");
        }
        else {
            dest.writeString(mAlert.toString());
        }

        dest.writeBundle(mExtras);
	}

	public static final Parcelable.Creator<Payload> CREATOR
	= new Parcelable.Creator<Payload>() {
		public Payload createFromParcel(Parcel in) {
			return new Payload(in);
		}

		public Payload[] newArray(int size) {
			return new Payload[size];
		}
	};

	private Payload(Parcel in) {
		this.mId = in.readString();
		this.mAlert = in.readString();
        this.mExtras = in.readBundle();
	}

	public static Payload fromIntent(Intent gcmIntent) {
		Payload notification = new Payload();
		try {

			String deliveryID = gcmIntent.getStringExtra("_nid");
			String body = gcmIntent.getStringExtra("body");

            Bundle extras = gcmIntent.getExtras();

            extras.remove(nidKey);
            extras.remove(bodyKey);

			if (deliveryID == null) {
				NPLog.e("no deliveryID");
			} else {
				notification.mId = deliveryID;
			}

			if (body==null) {
				NPLog.e("no body");
                body = "";
			}

            notification.mAlert = body;

            if(extras.size()==0) {
                NPLog.e("no extras");
            }
            else {
                NPLog.d("extras : "+extras);

                NPLog.d("extras keyset : "+extras.keySet());

                notification.mExtras = extras;
            }
			

		} catch (NullPointerException e) {
			NPLog.e("no extras from intent");
			e.printStackTrace();
		}
		return notification;
	}

	/**
	 * Returns a new notification from JSON String Payload (ex : {"id":42,"alert":"Alert Message"}).
	 *
	 * @param  jsonPayload  	jsonPayload. If empty or null, the notification returned will be null.
	 * @return     	 			a new notification from JSON String Payload.
	 */

	public static Payload fromJSON(String jsonPayload) {
		Gson gson = new Gson();
		Payload notification = gson.fromJson(jsonPayload, Payload.class);
		return notification;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param id the mId to set
	 */
	private void setId(String id) {
		this.mId = id;
	}

	/**
	 * @return the alert
	 */
	public String getAlert() {
		return mAlert;
	}

	/**
	 * @param alert the alert to set
	 */
	private void setAlert(String alert) {
		this.mAlert = mAlert;
	}

    /**
     * @return the extras
     */

    public Bundle getExtras() {
        return mExtras;
    }

    /**
     * @param extras the alert to set
     */
    private void setExtras(Bundle extras) {
        this.mExtras = extras;
    }
}
