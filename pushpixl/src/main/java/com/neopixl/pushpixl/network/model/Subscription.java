package com.neopixl.pushpixl.network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neopixl.pushpixl.PushPixlConstant;
import com.neopixl.pushpixl.model.QuietTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {

	private int id;
	private String type;
    private String alias;
	private String deviceToken;
	private boolean valid;
	private List<Tag> tags;
	private boolean disabled;
	private String timezone;
	private boolean production;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern=PushPixlConstant.NETWORK_FORMAT_DATE)
	private Date subscriptionDate;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern=PushPixlConstant.NETWORK_FORMAT_DATE_QUIETTIME)
	private Date startQuietTime;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern=PushPixlConstant.NETWORK_FORMAT_DATE_QUIETTIME)
	private Date endQuietTime;

	public Subscription() {
	}

	public Subscription(String alias, String newDeviceToken, QuietTime quietTimeRequest, List<String> tagsList, boolean valid, boolean disabled, boolean production) {
		super();
		this.endQuietTime = quietTimeRequest.getEndTime();
		this.startQuietTime = quietTimeRequest.getStartTime();
		this.type = PushPixlConstant.NP_SUBSCRIBTION_TYPE;
		this.alias = alias;
		this.deviceToken = newDeviceToken;
		this.timezone = TimeZone.getDefault().getID();
		this.valid = valid;
        this.disabled = disabled;
        this.production = production;

        List<Tag> newTagList = new ArrayList<>();
        for (String tagname : tagsList) {
			newTagList.add(new Tag(tagname));
		}
		this.tags = newTagList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
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

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Date getStartQuietTime() {
		return startQuietTime;
	}

	public void setStartQuietTime(Date startQuietTime) {
		this.startQuietTime = startQuietTime;
	}

	public Date getEndQuietTime() {
		return endQuietTime;
	}

	public void setEndQuietTime(Date endQuietTime) {
		this.endQuietTime = endQuietTime;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Subscription)) return false;

		Subscription that = (Subscription) o;

		if (getId() != that.getId()) return false;
		if (isValid() != that.isValid()) return false;
		if (isDisabled() != that.isDisabled()) return false;
		if (isProduction() != that.isProduction()) return false;
		if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null)
			return false;
		if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null)
			return false;
		if (getDeviceToken() != null ? !getDeviceToken().equals(that.getDeviceToken()) : that.getDeviceToken() != null)
			return false;
		if (getTags() != null ? !getTags().equals(that.getTags()) : that.getTags() != null)
			return false;
		if (getTimezone() != null ? !getTimezone().equals(that.getTimezone()) : that.getTimezone() != null)
			return false;
		if (getSubscriptionDate() != null ? !getSubscriptionDate().equals(that.getSubscriptionDate()) : that.getSubscriptionDate() != null)
			return false;
		if (getStartQuietTime() != null ? !getStartQuietTime().equals(that.getStartQuietTime()) : that.getStartQuietTime() != null)
			return false;
		return getEndQuietTime() != null ? getEndQuietTime().equals(that.getEndQuietTime()) : that.getEndQuietTime() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getType() != null ? getType().hashCode() : 0);
		result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
		result = 31 * result + (getDeviceToken() != null ? getDeviceToken().hashCode() : 0);
		result = 31 * result + (isValid() ? 1 : 0);
		result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
		result = 31 * result + (isDisabled() ? 1 : 0);
		result = 31 * result + (getTimezone() != null ? getTimezone().hashCode() : 0);
		result = 31 * result + (isProduction() ? 1 : 0);
		result = 31 * result + (getSubscriptionDate() != null ? getSubscriptionDate().hashCode() : 0);
		result = 31 * result + (getStartQuietTime() != null ? getStartQuietTime().hashCode() : 0);
		result = 31 * result + (getEndQuietTime() != null ? getEndQuietTime().hashCode() : 0);
		return result;
	}
}
