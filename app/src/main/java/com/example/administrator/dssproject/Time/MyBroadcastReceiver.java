package com.example.administrator.dssproject.Time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final int INVALID_SCHEDULE_ID = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int scheduleId = intent.getIntExtra(ScheduleQueue.ARG_SCHEDULE_ID, INVALID_SCHEDULE_ID);
        if (scheduleId == INVALID_SCHEDULE_ID) {
            return;
        }

        Intent updateUiIntent = new Intent(MainActivity.ALARM_INTENT_FILTER_ACTION);
        context.sendBroadcast(updateUiIntent);

        Schedule schedule = MainActivity.myAppDatabase.scheduleDAO().getASchedule(scheduleId);
        int layoutId = schedule.getLayoutId();
        MainActivity.getLayout(layoutId, scheduleId);

//        Log.e("LayoutId: ", layoutID);
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
    }
}
