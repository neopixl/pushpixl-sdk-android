package com.neopixl.pushpixlapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushNotificationReceiver extends FirebaseMessagingService {
    private static final String TAG = PushNotificationReceiver.class.getSimpleName();

    private static final int REQUEST_CODE_NOTIFICATION_PUSH = 1;
    private static final int NOTIFICATION_DEFAULT_ID = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // ***
        // ADDED FOR LIBRARY
        PendingIntent pendingIntent = null;
        Context context = getApplicationContext();

        Log.e(TAG, "DID Received a notification");
        Map<String, String> data = remoteMessage.getData();
        if (data == null) {
            return;
        }

        Log.e(TAG, "The bundle containt event type and ID");
        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.putExtra("remote_message", remoteMessage);
        newIntent.setFlags(1);
        pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE_NOTIFICATION_PUSH, newIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        if (pendingIntent != null) {
            int notificationId = remoteMessage.getMessageId() != null ? remoteMessage.getMessageId().hashCode() : NOTIFICATION_DEFAULT_ID;
            String title = context.getString(R.string.app_name);
            String description = remoteMessage.getNotification() != null ? remoteMessage.getNotification().getTitle() : "";
            NotificationUtil.createNewNotification(context, notificationId, pendingIntent, title, description);
        }
        // END
        // ***
    }
}
