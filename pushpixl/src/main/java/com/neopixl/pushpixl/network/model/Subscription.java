package com.neopixl.pushpixl.network.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neopixl.pushpixl.PushPixlConstant;
import com.neopixl.pushpixl.model.QuietTime;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Subscription {

	private String type;
    private String alias;
	private String deviceToken;
	private boolean valid;
	private List<String> tags;
	private String startQuietTime;
	private String endQuietTime;
	private boolean disabled;
	private String timezone;
    private boolean production;

	public Subscription(String alias, String newDeviceToken, QuietTime quietTimeRequest, List<String> tagsList, boolean valid, boolean disabled, boolean production) {
		super();
		this.setStartQuietTime(quietTimeRequest.getStartTime());
		this.setEndQuietTime(quietTimeRequest.getEndTime());
		this.setType(PushPixlConstant.NP_SUBSCRIBTION_TYPE);
		this.setTags(tagsList);
		this.setAlias(alias);
		this.setDeviceToken(newDeviceToken);
		this.timezone = TimeZone.getDefault().getID();
		this.valid = valid;
        this.disabled = disabled;
        this.production = production;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Subscription) {
			Subscription otherSubscription = (Subscription)o;
			return type.equals(otherSubscription.getType()) &&
					alias.equals(otherSubscription.getAlias()) &&
					deviceToken.equals(otherSubscription.getDeviceToken()) &&
					valid==otherSubscription.isValid() &&
					tags.equals(otherSubscription.getTags()) &&
					startQuietTime.equals(otherSubscription.getStartQuietTime()) &&
					endQuietTime.equals(otherSubscription.getEndQuietTime()) &&
					disabled == otherSubscription.isDisabled() &&
					timezone == otherSubscription.getTimezone() &&
					production == otherSubscription.isProduction();
		}

		return false;
	}

	public String jsonValue() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("HH:mmZZZZZ").create();
		   //.setDateFormat("HH:mmZZ").create();
		return gson.toJson(this);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getStartQuietTime() {
		return startQuietTime;
	}

	public void setStartQuietTime(String startQuietTime) {
		this.startQuietTime = startQuietTime;
	}

	public String getEndQuietTime() {
		return endQuietTime;
	}

	public void setEndQuietTime(String endQuietTime) {
		this.endQuietTime = endQuietTime;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

    public boolean isProduction() {
        return production;
    }

    public void setProduction(boolean production) {
        this.production = production;
    }

}
