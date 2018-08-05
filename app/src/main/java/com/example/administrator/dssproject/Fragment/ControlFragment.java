package com.example.administrator.dssproject.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.BoxActivity;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Utils.MediaView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    public static final String ARG_SCHEDULE_INFO = "argSenarioInfo";

    private Handler mHandler;
    private ScheduleInfo mScheduleInfo;
    private Context mContext;

    @NonNull
    private HashMap<String, List<MediaSrc>> mVideoPaths = new HashMap<>();
    private HashMap<String, List<PlaylistItem>> mPlaylistItemLists = new HashMap<String, List<PlaylistItem>>();

    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance(ScheduleInfo scheduleInfo) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHEDULE_INFO, scheduleInfo);
        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mScheduleInfo = (ScheduleInfo) args.getSerializable(ARG_SCHEDULE_INFO);
        }
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = container.getContext();

        String layout_name = "layout_" + mScheduleInfo.layoutId;
        int layoutId = getResources().getIdentifier("com.example.administrator.dssproject:layout/" + layout_name, null, null);
        // Inflate the layout for this fragment
        View view = inflater.inflate(layoutId, container, false);
//        int[] areaIds = {35, 36, 37, 38};
        int[] areaIds = MainActivity.myAppDatabase.areaDAO().areaIds(mScheduleInfo.layoutId);
        HashMap<String, List<ScenarioItem>> scenarioItemLists = new HashMap<>();
        for (int i : areaIds) {
            scenarioItemLists.put("area_" + i, new ArrayList<ScenarioItem>());
        }
        HashMap<String, List<Integer>> playlistLists = new HashMap<>();
        HashMap<String, List<PlaylistItem>> playlistItemLists = new HashMap<>();
        List<ScenarioItem> scenarioItemListDB = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScenarioId(mScheduleInfo.scenarioId);
        for (int i = 0; i < scenarioItemListDB.size(); i++) {
            int areaId = scenarioItemListDB.get(i).getAreaId();
            scenarioItemLists.put("area_" + areaId, MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(areaId));
        }
        for (int i : areaIds) {
            List<ScenarioItem> scenarioItemList = scenarioItemLists.get("area_" + i);
            List<Integer> playlistList = new ArrayList<>();
            for (ScenarioItem scenarioItem : scenarioItemList) {
                playlistList.add(scenarioItem.getPlaylistId());
            }
            playlistLists.put("area_" + i, playlistList);
        }
        for (int i : areaIds) {
            List<Integer> playlistList = playlistLists.get("area_" + i);
            List<PlaylistItem> playlistItemList = new ArrayList<>();
            List<MediaSrc> mediaSrcList = new ArrayList<>();
            for (int j : playlistList){
                List<PlaylistItem> playlistItemListDB = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(j);
                playlistItemList.addAll(playlistItemListDB);
                for (PlaylistItem playlistItem: playlistItemListDB){
                    MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItem.getPlaylistItemId());
                    mediaSrcList.add(mediaSrc);
                }
            }
            mVideoPaths.put("area_"+i, mediaSrcList);
            mPlaylistItemLists.put("area_"+i, playlistItemList);
            playlistItemLists.put("area_"+i, playlistItemList); //co ve la ko can thiet
        }

        List<MediaView> mediaViews = new ArrayList<>();
        for (int i : areaIds) {
            String area_name = "area_" + i;
            int areaId = getResources().getIdentifier("com.example.administrator.dssproject:id/" + area_name, null, null);
            final MediaView mediaView = view.findViewById(areaId);
            mediaView.setMediaSources(mPlaylistItemLists.get("area_"+i), mVideoPaths.get("area_"+i));
            mediaViews.add(mediaView);

        }

        scheduleMediaShowing(mediaViews);
        scheduleMediaStop(mediaViews);
        scheduleMediaDownloader();

        return view;
    }

    private void scheduleMediaDownloader() {
        long delayed = mScheduleInfo.endTime - System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30);
        if (delayed < 0) {
            delayed = 0;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Box> boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                if (boxes.size() == 0) {
                    Intent intent = new Intent(mContext, BoxActivity.class);
                    startActivity(intent);
                } else {
                    boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                    int boxId = boxes.get(0).getBoxId();
                    ApiData.getDataFromAPI(mContext, boxId);
                }
            }
        }, delayed);
    }

    private void scheduleMediaShowing(@NonNull final List<MediaView> mediaViews) {
        long delayed = mScheduleInfo.startTime > System.currentTimeMillis() ?
                mScheduleInfo.startTime - System.currentTimeMillis() : 0;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (MediaView view : mediaViews) {
                    view.startMedia();
                    Intent updateUiIntent = new Intent(MainActivity.ALARM_INTENT_FILTER_ACTION);
                    mContext.sendBroadcast(updateUiIntent);
                }
            }
        }, delayed);
    }

    private void scheduleMediaStop(@NonNull final List<MediaView> mediaViews) {
        long duration = mScheduleInfo.endTime - System.currentTimeMillis();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (MediaView view : mediaViews) {
                    view.stopMedia();
                }
            }
        }, duration);
    }

    public static class ScheduleInfo implements Serializable {
        int scenarioId;
        int layoutId;
        long startTime;
        long endTime;

        public ScheduleInfo(int scenarioId, int layoutId, long startTime, long endTime) {
            this.scenarioId = scenarioId;
            this.layoutId = layoutId;

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            this.startTime = startTime + cal.getTimeInMillis();
            this.endTime = endTime + cal.getTimeInMillis();
        }
    }
}
