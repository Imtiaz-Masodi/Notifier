package com.notifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Context context;
    int minutesCounter=1;
    TextView tvMinutes;
    Button up,down,start,stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        tvMinutes= (TextView) findViewById(R.id.tvMinutes);
        up= (Button) findViewById(R.id.bUp);
        down= (Button) findViewById(R.id.bDown);
        start= (Button) findViewById(R.id.bStart);
        stop= (Button) findViewById(R.id.bStop);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutesCounter++;
                tvMinutes.setText(minutesCounter+"");
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minutesCounter<=1) {
                    Toast.makeText(context, "Time gap should atleast be 1 minute", Toast.LENGTH_SHORT).show();
                } else {
                    minutesCounter--;
                    tvMinutes.setText(minutesCounter+"");
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int m=((calendar.get(Calendar.MINUTE)+minutesCounter)%60);
                int h=calendar.get(Calendar.HOUR);
                if(m<=minutesCounter)
                    h=((calendar.get(Calendar.HOUR)+1)%24);
                calendar.set(Calendar.HOUR ,h);
                calendar.set(Calendar.MINUTE ,m);
                calendar.set(Calendar.SECOND,2);

                final Intent myIntent = new Intent(context, AlarmReciever.class);
                PendingIntent pendingIndent = PendingIntent.getBroadcast(context, 301, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),(minutesCounter*60*1000),pendingIndent);
                Toast.makeText(context, "Starting Services . . .", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(context, AlarmReciever.class).putExtra("stop",true);
                PendingIntent pendingIndent = PendingIntent.getBroadcast(context, 301, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis(),pendingIndent);
                Toast.makeText(context, "Stopping Services . . .", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
