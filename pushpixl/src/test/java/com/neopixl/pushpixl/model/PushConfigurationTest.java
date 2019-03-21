package com.neopixl.pushpixl.model;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushConfigurationTest {

    List<String> defaultTags;
    QuietTime defaultQuietTime;
    String defaultHost;

    @Before
    public void setUp() throws Exception {
        defaultTags = new ArrayList<>();
        defaultTags.add("build-1");
        defaultTags.add("lang-fr");

        defaultQuietTime = new QuietTime(22, 0, 5, 0);

        defaultHost = "pushpixl.io";
    }

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getToken"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getSecret"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getTenant"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getHost"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("isDebug"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("isAutoRefresh"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("isAskBatteryOptimization"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getDefaultTags"));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("getDefaultQuietTime"));

        assertNotNull(PushConfiguration.class.getDeclaredConstructor(String.class, String.class, String.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("hostname",
                String.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("debug",
                boolean.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("autoRefresh",
                boolean.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("askBatteryOptimization",
                boolean.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("defaultTags",
                List.class));
        assertNotNull(PushConfiguration.class.getDeclaredMethod("defaultQuietTime",
                QuietTime.class));
    }

    @Test
    public void defaultValues() throws Exception {
        String token = "50465c62-a3e5-4bfb-9aa6-528395d3800e";
        String secret = "208b23a5-3aed-49fe-b999-f2ece02656dc";
        String tenant = "neopixl";

        PushConfiguration configuration = new PushConfiguration(
                token,
                secret
                , tenant);

        assertEquals("Should be equals", defaultHost, configuration.getHost());
        assertEquals("Should be equals", false, configuration.isDebug());
        assertEquals("Should be equals", true, configuration.isAutoRefresh());
        assertEquals("Should be equals", false, configuration.isAskBatteryOptimization());
        assertEquals("Should be equals", false, configuration.isUsingNotSecureHttp());
    }

    @Test
    public void fullConfigurationIsEquals() throws Exception {
        String token = "50465c62-a3e5-4bfb-9aa6-528395d3800e";
        String secret = "208b23a5-3aed-49fe-b999-f2ece02656dc";
        String tenant = "neopixl";
        String host = "neopixl.io";
        boolean debug = true;
        boolean autoRefresh = true;
        boolean batteryOptimization = true;
        boolean notSecureHttp = false;

        PushConfiguration configuration = new PushConfiguration(
                token,
                secret
                , tenant)
                .hostname(host)
                .debug(debug)
                .autoRefresh(autoRefresh)
                .askBatteryOptimization(batteryOptimization)
                .useNotSecureHttp(notSecureHttp)
                .defaultTags(defaultTags)
                .defaultQuietTime(defaultQuietTime);

        assertEquals("Should be equals", token, configuration.getToken());
        assertEquals("Should be equals", secret, configuration.getSecret());
        assertEquals("Should be equals", tenant, configuration.getTenant());
        assertEquals("Should be equals", host, configuration.getHost());
        assertEquals("Should be equals", debug, configuration.isDebug());
        assertEquals("Should be equals", notSecureHttp, configuration.isUsingNotSecureHttp());
        assertEquals("Should be equals", autoRefresh, configuration.isAutoRefresh());
        assertEquals("Should be equals", batteryOptimization, configuration.isAskBatteryOptimization());
        assertEquals("Should be equals", defaultTags, configuration.getDefaultTags());
        assertEquals("Should be equals", defaultQuietTime, configuration.getDefaultQuietTime());
    }

    @Test
    public void notSecureAllowedInDebug() throws Exception {
        String token = "50465c62-a3e5-4bfb-9aa6-528395d3800e";
        String secret = "208b23a5-3aed-49fe-b999-f2ece02656dc";
        String tenant = "neopixl";

        PushConfiguration configuration = new PushConfiguration(
                token,
                secret
                , tenant)
                .debug(true)
                .useNotSecureHttp(true);

        assertEquals("Should be equals", true, configuration.isUsingNotSecureHttp());
    }

    @Test
    public void notSecureNotAllowedInProd() throws Exception {
        String token = "50465c62-a3e5-4bfb-9aa6-528395d3800e";
        String secret = "208b23a5-3aed-49fe-b999-f2ece02656dc";
        String tenant = "neopixl";

        PushConfiguration configuration = new PushConfiguration(
                token,
                secret
                , tenant)
                .debug(false)
                .useNotSecureHttp(true);

        assertEquals("Should be equals", false, configuration.isUsingNotSecureHttp());
    }

    @Test(expected = IncorrectConfigurationException.class)
    public void configurationNotHandlingEmptyTags() throws Exception {
        List<String> emptyTags = new ArrayList<>();

        PushConfiguration configuration = new PushConfiguration(
                "50465c62-a3e5-4bfb-9aa6-528395d3800e",
                "208b23a5-3aed-49fe-b999-f2ece02656dc"
                , "neopixl")
                .defaultTags(emptyTags);
    }

    @Test(expected = IncorrectConfigurationException.class)
    public void configurationNotHandlingSpecialTags() throws Exception {
        List<String> tagList = new ArrayList<>();
        tagList.add("test:noWorking");

        PushConfiguration configuration = new PushConfiguration(
                "50465c62-a3e5-4bfb-9aa6-528395d3800e",
                "208b23a5-3aed-49fe-b999-f2ece02656dc"
                , "neopixl")
                .defaultTags(tagList);
    }
}
