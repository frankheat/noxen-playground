package dev.noxen.playground;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ServiceCompat;

/**
 * Target of startForegroundService(). Android requires such a service to call
 * startForeground() within a few seconds, otherwise it raises an ANR. This service
 * keeps that promise right away, then stops itself so no notification lingers.
 * Service1 stays a plain started service for the startService() scenario.
 */
public class ForegroundService1 extends WrapService {

    private static final String CHANNEL_ID = "noxen_playground_foreground";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        NotificationManagerCompat.from(this).createNotificationChannel(
                new NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW)
                        .setName("Foreground service scenario")
                        .build());
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("noxen playground")
                .setContentText("Foreground service scenario")
                .build();
        int type = Build.VERSION.SDK_INT >= 34 ? ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE : 0;
        ServiceCompat.startForeground(this, NOTIFICATION_ID, notification, type);
        Toast.makeText(this, "onStartCommand() executed (foreground)", Toast.LENGTH_SHORT).show();
        stopSelf();
        return START_NOT_STICKY;
    }
}
