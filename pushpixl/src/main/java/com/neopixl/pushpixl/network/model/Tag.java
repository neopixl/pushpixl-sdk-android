package com.neopixl.pushpixl.network.model;

import android.support.annotation.Nullable;

import java.util.List;

public class Tag {

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

	private String tagName;

	public Tag() {
		super();
	}

	public Tag(String tagName) {
		super();
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
