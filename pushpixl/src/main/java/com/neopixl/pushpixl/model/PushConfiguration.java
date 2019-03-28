package com.neopixl.pushpixl.model;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.util.TagsUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushConfiguration {
    private static final String HOSTNAME_DEFAULT = "pushpixl.io";

    @NonNull
    private String token;
    @NonNull
    private String secret;
    @NonNull
    private String tenant;
    @NonNull
    private String host;
    private boolean debug;
    private boolean autoRefresh;
    private boolean askBatteryOptimization;
    private boolean useNotSecureHttp;
    @Nullable
    private List<String> defaultTags;
    @Nullable
    private QuietTime defaultQuietTime;


    /**
     * Configure the configuration of push
     *
     * @param token  the Pushpixl token of the app
     * @param secret the Pushpixl secret of the app
     * @param tenant the Pushpixl tenant of the app
     */
    public PushConfiguration(@NonNull String token, @NonNull String secret, @NonNull String tenant) {
        this.token = token;
        this.secret = secret;
        this.tenant = tenant;

        this.host = HOSTNAME_DEFAULT;
        this.debug = false;
        this.autoRefresh = true;
        this.askBatteryOptimization = false;
        this.defaultTags = null;
        this.defaultQuietTime = null;
        this.useNotSecureHttp = false;
    }

    /**
     * Allow the configuration to use a different host name.
     * DEFAULT : pushpixl.io
     *
     * @param host the hostname of the server (Ex: pushpixl.io)
     * @return the current configuration
     */
    public PushConfiguration hostname(@NonNull String host) {
        this.host = host;
        return this;
    }

    /**
     * Allow the configuration to use a debug mode for push
     * DEFAULT : false
     *
     * @param debug boolean for launching the sdk in debug
     * @return the current configuration
     */
    public PushConfiguration debug(boolean debug) {
        this.debug = debug;
        return this;
    }

    /**
     * Allow the configuration to auto refresh the token if the user details are setted.
     * The refresh will be triggered every time the push token change
     * DEFAULT : true
     *
     * @param autoRefresh boolean for allowing the automatic refrehs
     * @return the current configuration
     */
    public PushConfiguration autoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
        return this;
    }

    /**
     * Allow the configuration to tigger the battery optimization popup
     * DEFAULT : false
     *
     * @param askBatteryOptimization boolean for allowing the battery optimization
     * @return the current configuration
     */
    public PushConfiguration askBatteryOptimization(boolean askBatteryOptimization) {
        this.askBatteryOptimization = askBatteryOptimization;
        return this;
    }

    /**
     * Allow the configuration handle some default tags.
     * They will be added to the user configuration
     * DEFAULT : @NULL
     *
     * @param defaultTags tag list
     * @return the current configuration
     */
    public PushConfiguration defaultTags(@Nullable List<String> defaultTags) {
        if (!TagsUtil.isValid(defaultTags)) {
            throw new IncorrectConfigurationException("Cannot set defaultTags to empty, and they cannot containt the char `:`");
        }

        this.defaultTags = defaultTags;
        return this;
    }

    /**
     * Allow the configuration handle some default quiet time.
     * The default quietTime has a lower priority than the given one at register
     * DEFAULT : @NULL
     *
     * @param defaultQuietTime the quiet time
     * @return the current configuration
     */
    public PushConfiguration defaultQuietTime(@Nullable QuietTime defaultQuietTime) {
        this.defaultQuietTime = defaultQuietTime;
        return this;
    }

    /**
     * Allow the SDK to use the not secure http:// scheme. This is not allowed in production mode
     * DEFAULT : false
     *
     * @param useNotSecureHttp if true the scheme will be http without ssl
     * @return the current configuration
     */
    public PushConfiguration useNotSecureHttp(boolean useNotSecureHttp) {
        this.useNotSecureHttp = useNotSecureHttp;
        return this;
    }

    /**
     * Get the app token for Pushpixl
     *
     * @return token, not null
     */
    @NonNull
    public String getToken() {
        return token;
    }

    /**
     * Get the app secret for Pushpixl
     *
     * @return app secret, not null
     */
    @NonNull
    public String getSecret() {
        return secret;
    }

    /**
     * Get the app tenant for Pushpixl
     *
     * @return app tenant, not null
     */
    @NonNull
    public String getTenant() {
        return tenant;
    }

    /**
     * Get the app hostname for Pushpixl
     *
     * @return hostname, not null
     */
    @NonNull
    public String getHost() {
        return host;
    }

    /**
     * Get the debug boolean
     *
     * @return debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Get the auto refresh boolean
     *
     * @return autoRefresh
     */
    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    /**
     * Get the configuration for using the not secure http:// scheme.
     * This is not allowed in production mode
     *
     * @return useNotSecureHttp
     */
    public boolean isUsingNotSecureHttp() {
        return useNotSecureHttp;
    }

    /**
     * Get the battery optimization boolean
     *
     * @return askBatteryOptimization
     */
    public boolean isAskBatteryOptimization() {
        return askBatteryOptimization;
    }

    /**
     * Get the defaults tags
     *
     * @return defaultTags
     */
    @Nullable
    public List<String> getDefaultTags() {
        return defaultTags;
    }

    /**
     * Get the default quiet time
     *
     * @return defaultQuietTime
     */
    @Nullable
    public QuietTime getDefaultQuietTime() {
        return defaultQuietTime;
    }
}
