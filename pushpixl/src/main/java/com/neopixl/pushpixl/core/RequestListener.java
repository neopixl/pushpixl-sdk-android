package com.neopixl.pushpixl.core;

import com.neopixl.pushpixl.core.domain.Error;

public interface RequestListener {
	public void registerSuccess(String registrationId);
	public void registerFailed(Error e);
	
	public void unregisterSuccess();
	public void unregisterFailed(Error e);
	
	public void confirmReadingSuccess();
	public void confirmReadingFailed(Error e);
}
