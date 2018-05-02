package com.neopixl.pushpixl.model;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class UserPreferencesTest {

    List<String> defaultTags;
    QuietTime defaultQuietTime;

    @Before
    public void setUp() throws Exception {
        defaultTags = new ArrayList<>();
        defaultTags.add("build-1");
        defaultTags.add("lang-fr");

        defaultQuietTime = new QuietTime(22, 0, 5, 0);
    }

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(UserPreferences.class.getDeclaredMethod("getAlias"));
        assertNotNull(UserPreferences.class.getDeclaredMethod("getTags"));
        assertNotNull(UserPreferences.class.getDeclaredMethod("getQuietTime"));

        assertNotNull(UserPreferences.class.getDeclaredConstructor(String.class));
        assertNotNull(UserPreferences.class.getDeclaredMethod("tags",
                List.class));
        assertNotNull(UserPreferences.class.getDeclaredMethod("quietTime",
                QuietTime.class));
    }

    @Test
    public void defaultValues() throws Exception {
        String alias = "user123";

        UserPreferences preferences = new UserPreferences(alias);

        assertNull("Should be equals", preferences.getQuietTime());
        assertNull("Should be equals", preferences.getTags());
    }

    @Test
    public void fullPreferencesIsEquals() throws Exception {
        String alias = "user123";

        UserPreferences preferences = new UserPreferences(alias)
                .tags(defaultTags)
                .quietTime(defaultQuietTime);

        assertEquals("Should be equals", alias, preferences.getAlias());
        assertEquals("Should be equals", defaultTags, preferences.getTags());
        assertEquals("Should be equals", defaultQuietTime, preferences.getQuietTime());
    }

    @Test(expected = IncorrectConfigurationException.class)
    public void configurationNotHandlingEmptyTags() throws Exception {
        List<String> emptyTags = new ArrayList<>();
        String alias = "user123";

        UserPreferences preferences = new UserPreferences(alias)
                .tags(emptyTags);
    }

    @Test(expected = IncorrectConfigurationException.class)
    public void configurationNotHandlingSpecialTags() throws Exception {
        List<String> tagList = new ArrayList<>();
        tagList.add("test:noWorking");
        String alias = "user123";

        UserPreferences preferences = new UserPreferences(alias)
                .tags(tagList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationNotHandlingEmptyAlias() throws Exception {
        List<String> emptyTags = new ArrayList<>();
        String alias = " ";

        UserPreferences preferences = new UserPreferences(alias);
    }
}
