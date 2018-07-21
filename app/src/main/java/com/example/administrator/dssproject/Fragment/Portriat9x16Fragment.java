package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;
import com.example.administrator.dssproject.Time.ScheduleQueue;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Portriat9x16Fragment extends Fragment {

    VideoView video1;
    VideoView video2;
    VideoView video3;

    public Portriat9x16Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_portriat9x16, container, false);
        video1 = view.findViewById(R.id.areaView1);
        video2 = view.findViewById(R.id.areaView2);
        video3 = view.findViewById(R.id.areaView3);

        List<Schedule> scheduleList = ScheduleQueue.checkValidStartTime();
        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScehduleId(scheduleList.get(0).getScheduleId());
        for(int i = 0; i < scenarioItemList.size(); i++){

        }

        return view;
    }




}
