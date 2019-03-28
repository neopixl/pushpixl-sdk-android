package com.neopixl.pushpixl.network;

import com.neopixl.pushpixl.PushpixlData;
import com.neopixl.pushpixl.model.QuietTime;
import com.neopixl.pushpixl.network.model.Subscription;
import com.neopixl.pushpixl.network.model.Tag;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Florian ALONSO on 4/27/18.
 * For Neopixl
 */
public class SubscriptionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSubscription() {
        String alias = "device-neopixl";
        String stringDeviceToken = "39873489732987423987";
        QuietTime quietTimeRequest = new QuietTime(22,0,7,0);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("tag30948");
        tags.add("tag10098");
        tags.add("03980938");
        boolean valid = true;
        boolean disabled = false;
        boolean production = false;

        Subscription subscription1 = new Subscription(alias, stringDeviceToken, quietTimeRequest, tags, valid, disabled, production);
        Subscription subscription2 = new Subscription(alias, stringDeviceToken, quietTimeRequest, tags, valid, disabled, production);
        Subscription subscription3 = new Subscription("_alias_", stringDeviceToken, quietTimeRequest, tags, valid, disabled, production);

        assertEquals(subscription1, subscription2);
        assertNotSame(subscription1, subscription3);
        assertNotSame(subscription2, subscription3);
    }

    @Test
    public void testSubscriptionEquality() {
        String alias = "device-neopixl";
        String stringDeviceToken = "39873489732987423987";
        QuietTime quietTimeRequest = new QuietTime(22,0,7,0);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("tag30948");
        tags.add("tag10098");
        tags.add("03980938");
        boolean valid = true;
        boolean disabled = false;
        boolean production = false;

        Subscription subscription = new Subscription(alias, stringDeviceToken, quietTimeRequest, tags, valid, disabled, production);

        assertEquals(subscription.getAlias(), alias);
        assertEquals(subscription.getDeviceToken(), stringDeviceToken);
        assertEquals(subscription.getStartQuietTime(), quietTimeRequest.getStartTime());
        assertEquals(subscription.getEndQuietTime(), quietTimeRequest.getEndTime());
        assertEquals(subscription.getType(), PushpixlData.NP_SUBSCRIBTION_TYPE);

        // Checking all tags are present
        for (Tag tag : subscription.getTags()) {
            assertTrue("The tag should be present", tags.contains(tag.getTagName()));
        }
    }
}
