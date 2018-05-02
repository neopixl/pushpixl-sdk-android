package com.neopixl.pushpixl;

import android.content.Context;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.listener.UserPreferencesListener;
import com.neopixl.pushpixl.model.PushConfiguration;
import com.neopixl.pushpixl.model.UserPreferences;
import com.neopixl.pushpixl.testtools.MockedContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlManagerTest {
    PushConfiguration configuration;
    Context context;

    @Before
    public void setUp() throws Exception {
        configuration = new PushConfiguration(
                "50465c62-a3e5-4bfb-9aa6-528395d3800e",
                "208b23a5-3aed-49fe-b999-f2ece02656dc"
                , "neopixl")
                .debug(true)
                .autoRefresh(true)
                .askBatteryOptimization(true);

        context = new MockedContext();
    }

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(PushpixlManager.class.getDeclaredMethod("getInstance"));
        assertNotNull(PushpixlManager.class.getDeclaredMethod("getConfiguration"));

        assertNotNull(PushpixlManager.class.getDeclaredConstructor(Context.class, PushConfiguration.class));
        assertNotNull(PushpixlManager.class.getDeclaredMethod("install",
                Context.class, PushConfiguration.class));
        assertNotNull(PushpixlManager.class.getDeclaredMethod("updateUserPreferences",
                UserPreferences.class, UserPreferencesListener.class));
        assertNotNull(PushpixlManager.class.getDeclaredMethod("updateUserPreferences",
                UserPreferences.class));
    }

    @Test
    public void installCreateAnInstance() throws Exception {
        PushpixlManager manager = PushpixlManager.install(context, configuration);

        assertEquals("The instance should be the same", manager, PushpixlManager.getInstance());
        String alias = "user123";
    }

    @Test(expected = IncorrectConfigurationException.class)
    public void configurationInstallIsMandatory() throws Exception {
        PushpixlManager.getInstance();
    }
}
