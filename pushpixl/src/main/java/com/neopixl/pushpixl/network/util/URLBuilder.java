package com.neopixl.pushpixl.network.util;

import android.content.Context;


public class URLBuilder {

	private static class Configuration {

		private String tenant;
		private String serverDomain;
		private String applicationToken;
		private String applicationSecret;
		private boolean useHttps;

		public Configuration(String tenant, String serverDomain, String applicationToken, String applicationSecret, boolean useHttps) {
			this.tenant = tenant;
			this.serverDomain = serverDomain;
			this.applicationToken = applicationToken;
			this.applicationSecret = applicationSecret;
			this.useHttps = useHttps;
		}

	}

	private static String HTTP_URL_PROTOCOL = "http://";
	private static String HTTPS_URL_PROTOCOL = "https://";
	
	private static String URL_BASE_DEV = "pushpixlint.neopixl.com";
	private static String URL_BASE_PROD = "pushpixl.io";

	private static String PATH_WS = "ws";

	private static String URL_REGISTRATION = "recipient";
	private static String URL_MESSAGE = "notification";
	private static String URL_PUSH_MY_SELF = "push/self";

	private static URLBuilder instance;
	private Configuration configuration;

	protected static URLBuilder getInstance() {
		if(instance == null) {
			instance = new URLBuilder();
		}
		return instance;
	}

	public static void setConfiguration(String tenant, java.lang.String serverDomain, java.lang.String applicationToken, java.lang.String applicationSecret, boolean useHttps) {
		Configuration configuration = new Configuration(tenant, serverDomain, applicationToken, applicationSecret, useHttps);
		URLBuilder.getInstance().configuration = configuration;
	}

	/**
	 * Get Push Server Url
	 * @return  push server url
	 */
	private static String getBaseUrl(Context context,String tenant) {

		String baseUrl = ""; // TODO : USE CONFIGURATION

		if(null==baseUrl) {
			baseUrl = URL_BASE_PROD;
		}
		else {
			if(baseUrl.length()==0) {
				baseUrl = URL_BASE_PROD;
			}
		}

		String protocolUrl = HTTPS_URL_PROTOCOL;

		String urlTenant = "";

		if(null!=tenant && tenant.length()>0) {
			urlTenant = tenant+".";
		}

		return protocolUrl+urlTenant+baseUrl+"/"+PATH_WS;
	}

	/**
	 * Get Subscription Url
	 * @return
	 */
	public static String getSubscriptionUrl(Context context,String tenant) {
		String baseUrl = URLBuilder.getBaseUrl(context,tenant);
		return baseUrl+"/"+URL_REGISTRATION;
	}

	/**
	 * Get Unsubscription url
	 * @param registrationId
	 * @return
	 */
	public static String getUnsubscriptionUrl(Context context,String tenant,String registrationId) {
		String baseUrl = URLBuilder.getBaseUrl(context,tenant);
		return baseUrl+"/"+URL_REGISTRATION+"/"+registrationId;
	}

	/**
	 * Get ReadMessageUrl
	 * @param context
	 * @param tenant
	 * @return
	 */
	public static String getReadMessageUrl(Context context,String tenant) {
		String baseUrl = URLBuilder.getBaseUrl(context,tenant);
		return baseUrl+"/"+URL_MESSAGE+"/consume/";
	}
	
	public static String getPushMySelf(Context context,String tenant,String deviceToken) {
		String baseUrl = URLBuilder.getBaseUrl(context,tenant);
		return baseUrl+"/"+URL_PUSH_MY_SELF+"/"+deviceToken;
	}

}
