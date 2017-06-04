package com.example.rakkant.reminderapp;

/**
 * Created by rakkant on 6/3/2017 AD.
 */

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmManager extends AppCompatActivity {

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    android.app.AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (android.app.AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void OnToggleClicked(View view)
    {
        long time;
        if (((ToggleButton) view).isChecked()){
            Toast.makeText(AlarmManager.this, "SET ALARM ON",Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)  time = time + (1000*60*60*12);
                else       time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);

        }else{
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmManager.this, "SET ALARM OFF",Toast.LENGTH_LONG).show();
        }
    }

    public void backHome(View view){
        Intent i=new Intent(AlarmManager.this, Todolist.class);
        startActivity(i);
    }
}