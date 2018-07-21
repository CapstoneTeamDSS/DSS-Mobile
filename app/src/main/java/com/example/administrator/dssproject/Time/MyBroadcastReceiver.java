package com.example.administrator.dssproject.Time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final int INVALID_SCHEDULE_ID = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int scheduleId = intent.getIntExtra(ScheduleQueue.ARG_SCHEDULE_ID, INVALID_SCHEDULE_ID);
        if (scheduleId == INVALID_SCHEDULE_ID) {
            return;
        }

        Schedule schedule = MainActivity.myAppDatabase.scheduleDAO().getASchedule(scheduleId);
        int layoutId = schedule.getLayoutId();
        Log.e("Schedule", String.valueOf(layoutId));
//        MainActivity.getLayout(layoutId);
//        String layoutID = Integer.toString(layoutId);

//        Log.e("LayoutId: ", layoutID);
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
    }
}
