package com.notifier;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by MOHD IMTIAZ on 22-Oct-17.
 */

public class AlarmReciever extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean stop=intent.getBooleanExtra("stop",false);
        if(!stop) {
            NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Reminder Notification")
                    .setContentText("Slide to close notification")
                    .setAutoCancel(true);
            notificationManager.notify(100,builder.build());

            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(3000);
        }
    }
}
