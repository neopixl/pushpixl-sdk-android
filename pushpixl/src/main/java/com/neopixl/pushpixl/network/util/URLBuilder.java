package com.neopixl.pushpixl.network.util;

import com.neopixl.pushpixl.model.PushConfiguration;


public class URLBuilder {

	private static String HTTPS_URL_PROTOCOL = "https://";

	private static String PATH_WS = "ws";

	private static String URL_REGISTRATION = "recipient";
	private static String URL_MESSAGE = "notification";
	private static String URL_PUSH_MY_SELF = "push/self";

	private static URLBuilder instance;
	private PushConfiguration configuration;

	public static URLBuilder getInstance() {
		if(instance == null) {
			instance = new URLBuilder();
		}
		return instance;
	}

	public static void setConfiguration(PushConfiguration configuration) {
		URLBuilder.getInstance().configuration = configuration;
	}

	/**
	 * Get Push Server Url
	 * @return  push server url
	 */
	private String getBaseUrl() {

		String baseUrl = configuration.getHost();
		String protocolUrl = HTTPS_URL_PROTOCOL;
		String urlTenant = configuration.getTenant();

		return protocolUrl + urlTenant + "." + baseUrl + "/" + PATH_WS;
	}

	/**
	 * Get Subscription Url
	 * @return
	 */
	public String getSubscriptionUrl() {
		return getBaseUrl()+"/"+URL_REGISTRATION;
	}

	/**
	 * Get Unsubscription url
	 * @param registrationId
	 * @return
	 */
	public String getUnsubscriptionUrl(String registrationId) {
		return getBaseUrl()+"/"+URL_REGISTRATION+"/"+registrationId;
	}

	/**
	 * Get ReadMessageUrl
	 * @return
	 */
	public String getReadMessageUrl() {
		return getBaseUrl()+"/"+URL_MESSAGE+"/consume/";
	}
	
	public String getPushMySelf(String deviceToken) {
		return getBaseUrl()+"/"+URL_PUSH_MY_SELF+"/"+deviceToken;
	}

}
