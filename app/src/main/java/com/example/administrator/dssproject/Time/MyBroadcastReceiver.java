package com.example.administrator.dssproject.Time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.MainActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final int INVALID_SCENARIO_ID = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int scenarioId = intent.getIntExtra(ScheduleQueue.ARG_SCENARIO_ID, INVALID_SCENARIO_ID);
        if (scenarioId == INVALID_SCENARIO_ID) {
            return;
        }

        Intent updateUiIntent = new Intent(MainActivity.ALARM_INTENT_FILTER_ACTION);
        context.sendBroadcast(updateUiIntent);

        Scenario scenario = MainActivity.myAppDatabase.scenarioDAO().getAScenario(scenarioId);
        int layoutId = scenario.getLayoutId();
        MainActivity.getLayout(layoutId, scenarioId);

//        Log.e("LayoutId: ", layoutID);
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
    }
}
