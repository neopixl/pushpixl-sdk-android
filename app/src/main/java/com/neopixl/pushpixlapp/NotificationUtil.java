package com.neopixl.pushpixlapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by Florian ALONSO on 7/18/17.
 * For Neopixl
 */

public class NotificationUtil {
    private static final String CHANNEL_ID_DEFAULT = "CERBA_NOTIFICATION_CHANNEL_DEFAULT";

    public static void init(Context context) {
        initDefaultChannel(context);
    }

    private static NotificationChannel initDefaultChannel(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return null;
        }
        NotificationChannel channel = getDefaultChannel(context);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        return channel;
    }

    private static NotificationChannel getDefaultChannel(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return null;
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        CharSequence channelName = context.getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID_DEFAULT, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(ContextCompat.getColor(context, R.color.colorAccent));
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationChannel.setShowBadge(true);
        notificationChannel.setSound(defaultSoundUri, null);
        return notificationChannel;
    }

    public static void createNewNotification(Context context, int notificationId, PendingIntent pendingIntent, String longTitle, String description) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int icon = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = R.drawable.ic_launcher_background;
        } else {
            icon = R.mipmap.ic_launcher;
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(icon)
                .setContentTitle(longTitle)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setTicker(longTitle)
                .setSound(defaultSoundUri)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setLights(ContextCompat.getColor(context, R.color.colorAccent), 600, 600)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
