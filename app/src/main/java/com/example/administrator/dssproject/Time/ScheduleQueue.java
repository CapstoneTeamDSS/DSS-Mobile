package com.example.administrator.dssproject.Time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class ScheduleQueue {

    public static final String ARG_SCHEDULE_ID = "argScheduleId";

    public static void startSchedule(Context context, AlarmManager alarmManager){

        List<Schedule> scheduleList = checkValidStartTime();
        List<Schedule> scheduleListOrdered = scheduleListOrderStartTime(scheduleList);
        String startTime = scheduleListOrdered.get(0).getStartTime();
        int scheduleId = scheduleListOrdered.get(0).getScheduleId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long milliseconds = 0;
        try{
            Date d = simpleDateFormat.parse(startTime);
            milliseconds = d.getTime();
            milliseconds = milliseconds - System.currentTimeMillis();
        }catch (ParseException e){
            Log.e("Parse Time: ", e.toString());
        }
        /*Bundle bundle = new Bundle();
        bundle.putInt("SCHEDULEID", scheduleId);*/

        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        intent.putExtra(ARG_SCHEDULE_ID, scheduleId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + milliseconds
                , pendingIntent);
    }



    //get ScheduleList have startTime before 5m
    public static List<Schedule> checkValidStartTime(){
        List<Schedule> scheduleList = MainActivity.myAppDatabase.scheduleDAO().getSchedules();
        List<Schedule> newScheduleList = new ArrayList<Schedule>();
        for(int i = 0; i < scheduleList.size(); i++){
            String startTime = scheduleList.get(i).getStartTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            long milliseconds = 0;
            try{
                Date d = simpleDateFormat.parse(startTime);
                milliseconds = d.getTime();
                milliseconds = milliseconds - System.currentTimeMillis();
            }catch (ParseException e){
                Log.e("Parse Time: ", e.toString());
            }
            if (milliseconds > 300000){
                newScheduleList.add(scheduleList.get(i));
            }
        }
        return newScheduleList;
    }


    public static List<Schedule> scheduleListOrderStartTime(List<Schedule> scheduleList){
        Collections.sort(scheduleList);
        return scheduleList;
    }
}
