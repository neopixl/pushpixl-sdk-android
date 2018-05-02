package com.neopixl.pushpixl.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.util.TagsUtil;

import java.util.List;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class UserPreferences {

    @NonNull private String alias;
    @Nullable private List<String> tags;
    @Nullable private QuietTime quietTime;

    /**
     * Configure the user preferences
     * @param alias the ID of the user
     */
    public UserPreferences(@NonNull String alias) {
        if(alias.trim().isEmpty()) {
            throw new IllegalArgumentException("The alias cannot be empty");
        }
        this.alias = alias;
    }

    /**
     *  Allow the user to use a quiet time
     *  If not setted the default quiet time will be used
     *  DEFAULT : @Null
     *
     * @param quietTime the quiet time
     * @return the current preferences
     */
    public UserPreferences quietTime(@Nullable QuietTime quietTime) {
        this.quietTime = quietTime;
        return this;
    }

    /**
     *  Allow the user to use some tags to subscribe
     *  If not setted the defaults tags will be used
     *  DEFAULT : @Null
     *
     * @param tags the tag list
     * @return the current preferences
     */
    public UserPreferences tags(@Nullable List<String> tags) {
        if (!TagsUtil.isValid(tags)) {
            throw new IncorrectConfigurationException("Cannot set defaultTags to empty, and they cannot containt the char `:`");
        }

        this.tags = tags;
        return this;
    }

    /**
     * Get the alias of the user
     *
     * @return alias, not null
     */
    @NonNull
    public String getAlias() {
        return alias;
    }

    /**
     * Get the tags of the user
     *
     * @return tag list, not null
     */
    @Nullable
    public List<String> getTags() {
        return tags;
    }

    /**
     * Get the quiet time of the user
     *
     * @return quiet time, not null
     */
    @Nullable
    public QuietTime getQuietTime() {
        return quietTime;
    }
}
