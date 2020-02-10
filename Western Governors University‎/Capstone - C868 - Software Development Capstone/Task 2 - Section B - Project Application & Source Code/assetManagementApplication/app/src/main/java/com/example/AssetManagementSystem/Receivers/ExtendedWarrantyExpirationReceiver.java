package com.example.AssetManagementSystem.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;

import com.example.degreetracker.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ExtendedWarrantyExpirationReceiver extends BroadcastReceiver {

    static int notificationID = 300;
    String channel_id = "Product Start";

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_assessment)
                .setContentTitle("Warranty Notification")
                .setContentText("Warranty Due Date Today!").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);

    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {

        if (android.os.Build.VERSION.SDK_INT >= 26) {

            CharSequence name = "Warranty Due Channel";
            String description = "Warranty Due Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
