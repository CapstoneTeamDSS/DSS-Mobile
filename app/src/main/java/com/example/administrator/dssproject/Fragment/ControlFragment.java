package com.example.administrator.dssproject.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Utils.MediaView;
import com.example.administrator.dssproject.Utils.PreferenceUtil;
import com.example.administrator.dssproject.Utils.Supporter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */

public class ControlFragment extends Fragment {

    public static final String ARG_SCHEDULE_INFO = "argScenarioInfo";

    private Handler mHandler;
    private ScheduleInfo mScheduleInfo;
    private Context mContext;

    @NonNull
    private HashMap<String, List<MediaSrc>> mVideoPaths = new HashMap<>();
    private HashMap<String, List<PlaylistItem>> mPlaylistItemLists = new HashMap<>();

    private int[] mAreaIds;

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

        mAreaIds = MainActivity.myAppDatabase.areaDAO().areaIds(mScheduleInfo.layoutId);

        HashMap<String, List<ScenarioItem>> scenarioItemLists = new HashMap<>();
        for (int areaId : mAreaIds) {
            scenarioItemLists.put("area_" + areaId, new ArrayList<ScenarioItem>());
        }

        List<ScenarioItem> scenarioItemListDB = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByScenarioId(mScheduleInfo.scenarioId);
        for (ScenarioItem item : scenarioItemListDB) {
            int areaId = item.getAreaId();
            List<ScenarioItem> items = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(areaId);
            scenarioItemLists.put("area_" + areaId, items);
        }

        HashMap<String, List<Integer>> playlistLists = new HashMap<>();
        for (int areaId : mAreaIds) {
            List<ScenarioItem> scenarioItemList = scenarioItemLists.get("area_" + areaId);
            List<Integer> playlistList = new ArrayList<>();
            for (ScenarioItem scenarioItem : scenarioItemList) {
                playlistList.add(scenarioItem.getPlaylistId());
            }
            playlistLists.put("area_" + areaId, playlistList);
        }

        for (int areaId : mAreaIds) {
            List<Integer> playlistList = playlistLists.get("area_" + areaId);
            List<PlaylistItem> playlistItemList = new ArrayList<>();
            List<MediaSrc> mediaSrcList = new ArrayList<>();
            for (int j : playlistList) {
                List<PlaylistItem> playlistItemListDB = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(j);
                playlistItemList.addAll(playlistItemListDB);
                for (PlaylistItem playlistItem : playlistItemListDB) {
                    MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItem.getPlaylistItemId());
                    mediaSrcList.add(mediaSrc);
                }
            }
            mVideoPaths.put("area_" + areaId, mediaSrcList);
            mPlaylistItemLists.put("area_" + areaId, playlistItemList);
        }
    }

    //Check Hash Null

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();

        String layoutName = "layout_" + mScheduleInfo.layoutId;
        int layoutId = getResources().getIdentifier(
                "com.example.administrator.dssproject:layout/" + layoutName, null, null);
        View view = inflater.inflate(layoutId, container, false);

        new ShowMediaTask().execute();

        return view;
    }

    private class ShowMediaTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            for (int areaId : mAreaIds)
                for (MediaSrc m : mVideoPaths.get("area_" + areaId)) {
                    checkMd5Code(m);
                    /*String md5 = fileToMD5(m.getUrlLocal());
                    if (m.getHashCode() != null) {
                        if (!m.getHashCode().equals(md5)) {
                            String localUrl = Supporter.saveDataToSDCard(getContext(), m.getUrl(), m.getTitle(), m.getExtension());
                            m.setUrlLocal(localUrl);

                        }
                    }*/
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            List<MediaView> mediaViews = new ArrayList<>();
            int audioArea = MainActivity.myAppDatabase.scenarioDAO().getAudioArea(mScheduleInfo.scenarioId);
            for (int areaId : mAreaIds) {
                boolean isMute = areaId != audioArea;

                String areaName = "area_" + areaId;
                int areaResId = getResources().getIdentifier("com.example.administrator.dssproject:id/" + areaName, null, null);
                final MediaView mediaView = ((Activity) mContext).findViewById(areaResId);

                mediaView.setMediaSources(mPlaylistItemLists.get("area_" + areaId), mVideoPaths.get("area_" + areaId), isMute);
                mediaViews.add(mediaView);
            }

            PreferenceUtil.saveAppStatus(mContext, true);

            scheduleMediaShowing(mediaViews);
            scheduleMediaStop(mediaViews);
            scheduleMediaDownloader();
        }

        private void scheduleMediaDownloader() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int boxId = PreferenceUtil.getBoxId(mContext);
                    if (boxId == PreferenceUtil.DEFAULT_BOX_ID) {
                        Intent intent = new Intent(mContext, BoxActivity.class);
                        startActivity(intent);
                    } else {
                        ApiData.getDataFromAPI(mContext, boxId, true);
                    }
                }
            }, TimeUnit.MINUTES.toMillis(1));
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
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (MediaView view : mediaViews) {
                        PreferenceUtil.saveAppStatus(mContext, false);
                        view.stopMedia();
                    }
                }
            }, duration);
        }
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

    public static String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead);
            }
            byte[] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static String convertHashToString(byte[] md5Bytes) {
        StringBuilder returnVal = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            returnVal.append(Integer.toString((md5Byte & 0xff) + 0x100, 16).substring(1));
        }
        return returnVal.toString();
    }

    private void checkMd5Code(MediaSrc mediaSrc){
        String md5 = fileToMD5(mediaSrc.getUrlLocal());
        if (mediaSrc.getHashCode() != null) {
            if (!mediaSrc.getHashCode().equals(md5)) {
                String localUrl = Supporter.saveDataToSDCard(getContext(), mediaSrc.getUrl(), mediaSrc.getTitle(), mediaSrc.getExtension());
                mediaSrc.setUrlLocal(localUrl);
                checkMd5Code(mediaSrc);
            }
        }
    }
}