package com.neopixl.pushpixl.core;

import android.content.Context;
import android.os.Build;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.neopixl.logger.NPLog;
import com.neopixl.pushpixl.core.conf.PushPixlConstant;
import com.neopixl.pushpixl.core.domain.Error;
import com.neopixl.pushpixl.core.domain.Payload;
import com.neopixl.pushpixl.core.domain.QuietTime;
import com.neopixl.pushpixl.core.domain.Subscription;
import com.neopixl.pushpixl.core.exception.IncorrectTagException;
import com.neopixl.pushpixl.core.network.PushPixlJsonRequest;
import com.neopixl.pushpixl.core.network.PushPixlRequest;
import com.neopixl.pushpixl.core.network.URLBuilder;
import com.neopixl.pushpixl.core.util.GCMUtil;
import com.neopixl.pushpixl.core.util.GCMUtilRegistrationListener;
import com.neopixl.pushpixl.core.util.PushPixlContext;
import com.neopixl.pushpixl.core.util.PushPixlManifest;
import com.neopixl.pushpixl.core.util.TagsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationManager {

	private static NotificationManager mSharedInstance = null;

    private RequestQueue mRequestQueue;

	private static NotificationManager sharedNotificationManager() {
		if (mSharedInstance == null) {
			mSharedInstance = new NotificationManager();
		}
		return mSharedInstance;
	}

	private NotificationManager() {
		mRequestQueue = Volley.newRequestQueue(getContext());
	}

	private static Context getContext()
	{
		return PushPixlContext.getInstance().getContext();
	}

    public static void setConfiguration(String tenant, String serverDomain, String applicationToken, String applicationSecret, boolean useHttps) {
        URLBuilder.setConfiguration(tenant, serverDomain, applicationToken, applicationSecret, useHttps);
    }

    private static String getUrlRegistration(String tenant) {

        String urlRegistration = URLBuilder.getSubscriptionUrl(getContext(), tenant);

        return urlRegistration;
    }

    /**
     * Returns a generated device alias (usefull with {@link @see com.neopixl.pushpixl.core.RequestManager#registerDevice} )
     */

    public static String getDeviceAlias() {
        String deviceName = Build.BRAND+"_"+Build.MODEL+"_"+Build.DEVICE+"_"+Build.SERIAL;
        return deviceName;
    }

	/**
	 * Returns a new QuietTime object initialized with startTime
	 * (startHour:startMinute + Time Zone) and endTime(endHour:endMinute +
	 * TimeZone). The time used is the one set in the smartphone.
	 * <p>
	 * This method always returns immediately, whether or not the image exists.
	 * When this applet attempts to draw the image on the screen, the data will
	 * be loaded. The graphics primitives that draw the image will incrementally
	 * paint on the screen.
	 *
	 * @param tags
	 *            the end hour used for the quiet time
	 * @param alias
	 *            the end minute used for the quiet time
	 * @param quietTime
	 *            the Quiet Time
	 * @see QuietTime
     *
	 */
	public static void registerDevice(ArrayList<String> tags,
			String alias, QuietTime quietTime)
	{
		registerDevice(tags, alias, quietTime,null);
	}

	/**
	 * Returns a new QuietTime object initialized with startTime
	 * (startHour:startMinute + Time Zone) and endTime(endHour:endMinute +
	 * TimeZone). The time used is the one set in the smartphone.
	 * <p>
	 * This method always returns immediately, whether or not the image exists.
	 * When this applet attempts to draw the image on the screen, the data will
	 * be loaded. The graphics primitives that draw the image will incrementally
	 * paint on the screen.
	 *
	 * @param tags
	 *            the end hour used for the quiet time
	 * @param alias
	 *            the end minute used for the quiet time
	 * @param quietTime
	 *            the Quiet Time
	 * @param requestListener
	 *            listener
	 * @see QuietTime
	 */
	public static void registerDevice(final ArrayList<String> tags,
			final String alias, final QuietTime quietTime, final RequestListener requestListener) {

        //basic exception management
        if(null==alias) {
            NPLog.e("alias must not be null");
            return;
        }

        if(alias.trim().length()==0) {
            NPLog.e("alias must not be empty");
            return;
        }

        int numberOfTags = 0;

        if(null!=tags) {
            numberOfTags = tags.size();
        }

        for(int i=0;i<numberOfTags;i++) {
            String tag = tags.get(i);
            boolean isIncorrectTag = false;

            if(null!=tag) {
                isIncorrectTag = tag.split(":").length>1;
            }

            if(isIncorrectTag) {
                throw new IncorrectTagException("The tag "+tag+" contains ':'. This character is not allowed.");
            }
        }

        GCMUtil.register(new GCMUtilRegistrationListener() {
            @Override
            public void didReceiveRegistrationId(final String registrationId) {
                if (registrationId.equals(PushPixlConstant.NP_ERROR)) {
                    if (requestListener != null) {
                        requestListener.registerFailed(new Error(-1, "registration id invalid"));
                    }
                    return;
                }

                String tenant = PushPixlManifest.getTenantID(getContext());

                String urlRegistration = getUrlRegistration(tenant);    //URLBuilder.getSubscriptionUrl(getContext(), tenant);

                ArrayList<String> enhancedTags = new ArrayList<String>();

                if (null != tags) {
                    for(int i=0;i<tags.size();i++) {
                        String tag = tags.get(i);

                        if(null!=tag) {
                            enhancedTags.add(tag);
                        }
                    }
                }

                enhancedTags.addAll(TagsUtil.getAdditionalTags());

                boolean isInReleaseMode = PushPixlManifest.isInReleaseMode(PushPixlContext.getInstance().getContext());
                boolean isDisabled = false;
                boolean isValid = true;

                Subscription sub = new Subscription(alias, registrationId, quietTime, enhancedTags, isValid, isDisabled, isInReleaseMode);

                String json = sub.jsonValue();

                JSONObject jsonRequest = null;

                try {
                    jsonRequest = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                PushPixlJsonRequest request = new PushPixlJsonRequest(Request.Method.POST,urlRegistration,jsonRequest,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (requestListener != null) {
                            requestListener.registerSuccess(registrationId);
                        } else {
                            NPLog.d("device is now registered!");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dispatchErrorToListener(volleyError, requestListener);
                    }
                });

                request.setTag("register");
                request.addUserAndPassword(PushPixlManifest.getToken(getContext()), PushPixlManifest.getSecretKey(getContext()));

                sharedNotificationManager().mRequestQueue.add(request);

            }
        });
	}

    public static void confirmReading(Payload payload, RequestListener requestListener) {
        if(payload==null) {
            NPLog.w("Payload is null. Nothing to do.");
            return;
        }

        confirmReading(payload.getId(), requestListener);
    }

    public static void confirmReading(Payload payload) {
        if(payload==null) {
            NPLog.w("Payload is null. Nothing to do.");
            return;
        }

        confirmReading(payload.getId(), null);
    }

	public static void confirmReading(final String notifID, final RequestListener requestListener) {

		if (notifID == null) {
			NPLog.i("notification is null. Nothing to do.");
			return;
		}

        NPLog.d("confirmReading notif : "+notifID);

        GCMUtil.register(new GCMUtilRegistrationListener() {
            @Override
            public void didReceiveRegistrationId(String registrationId) {
                if (registrationId.equals(PushPixlConstant.NP_ERROR)) {
                    NPLog.e("Your registration ID can be retrieved.");
                    return;
                }

                String tenant = PushPixlManifest.getTenantID(getContext());

                String urlConfirmReading = URLBuilder.getReadMessageUrl(getContext(), tenant);

                PushPixlRequest request = new PushPixlRequest(Request.Method.POST,urlConfirmReading,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {

                        if (requestListener != null) {
                            requestListener.confirmReadingSuccess();
                        } else {
                            NPLog.d("confirm reading");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dispatchErrorToListener(volleyError, requestListener);
                    }
                });

                request.addUserAndPassword(PushPixlManifest.getToken(getContext()), PushPixlManifest.getSecretKey(getContext()));
                request.addParameter("deviceToken", registrationId);
                request.addParameter("notificationId",notifID);
                request.addParameter("provider",PushPixlConstant.NP_SUBSCRIBTION_TYPE);

                request.setTag("CONFIRM_READING");

                NotificationManager.sharedNotificationManager().mRequestQueue.add(request);
            }
        });

	}

	/*
	 * POST /ws/push/self/{deviceToken}
avec en param :
appKey : l'appKey
messageBody : le corps du message 
	 */

	public static void pushToMySelf(final String message)
	{

        GCMUtil.register(new GCMUtilRegistrationListener() {
            @Override
            public void didReceiveRegistrationId(String registrationId) {
                if (registrationId.equals(PushPixlConstant.NP_ERROR)) {
                    NPLog.e("Your registration ID can be retrieved.");
                    return;
                }

                Context currentContext = getContext();

                String tenant = PushPixlManifest.getTenantID(getContext());

                String url = ""; //URLBuilder.getPushMySelf(getContext(), tenant, registrationId);

                PushPixlRequest request = new PushPixlRequest(Request.Method.POST,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        NPLog.d("confirmReading onResponse : \""+string+"\"");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NPLog.d("confirmReading onErrorResponse : "+volleyError);
                    }
                });

                request.addUserAndPassword(PushPixlManifest.getToken(currentContext), PushPixlManifest.getSecretKey(getContext()));
                request.addParameter("messageBody",message);
                request.addParameter("appKey",PushPixlManifest.getToken(currentContext));

                String isInProductionValue = Boolean.valueOf(PushPixlManifest.isInReleaseMode(currentContext)).toString();
                request.addParameter("prod",isInProductionValue);

                request.setTag("SEND_MESSAGE");
                sharedNotificationManager().mRequestQueue.add(request);
            }
        });
	}
	

	public static void unregisterDevice() {
		unregisterDevice(null);
	}

	public static void unregisterDevice(final RequestListener requestListener) {

        GCMUtil.register(new GCMUtilRegistrationListener() {
            @Override
            public void didReceiveRegistrationId(String registrationId) {
                if (registrationId.equals(PushPixlConstant.NP_ERROR)) {
                    return;
                }

                String tenant = PushPixlManifest.getTenantID(getContext());

                String urlUnregister = URLBuilder.getUnsubscriptionUrl(getContext(), tenant, registrationId);

                PushPixlRequest nRequest = new PushPixlRequest(Request.Method.DELETE, urlUnregister, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (requestListener != null) {
                            requestListener.unregisterSuccess();
                        } else {
                            NPLog.d("device is now unregistered!");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dispatchErrorToListener(volleyError, requestListener);
                    }
                });

                nRequest.addUserAndPassword(PushPixlManifest.getToken(getContext()), PushPixlManifest.getSecretKey(getContext()));

                sharedNotificationManager().mRequestQueue.add(nRequest);

            }
        });
	}

    private static void dispatchErrorToListener(VolleyError volleyError, RequestListener requestListener) {

        if(null==volleyError) {
            return;
        }

        if (requestListener != null) {
            NetworkResponse networkResponse = volleyError.networkResponse;

            int statusCode = 0;

            if(null!=networkResponse) {
                statusCode = networkResponse.statusCode;
            }

            requestListener.registerFailed(new Error(statusCode, "can't register device"));

        } else {
            NetworkResponse networkResponse = volleyError.networkResponse;

            if(null!=networkResponse) {
                NPLog.e("problem (Network error:+" + networkResponse.statusCode + "+)");
            }
            else {
                NPLog.e("problem (Network error)");
            }

        }
    }

}
