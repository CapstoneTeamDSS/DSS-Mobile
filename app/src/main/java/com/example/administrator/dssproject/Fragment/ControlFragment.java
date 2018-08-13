package com.example.administrator.dssproject.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.BoxActivity;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.SDCard.CheckForSDCard;
import com.example.administrator.dssproject.Utils.MediaView;
import com.example.administrator.dssproject.Utils.Supporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

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

        int audioArea = MainActivity.myAppDatabase.scenarioDAO().getAudioArea(mScheduleInfo.scenarioId);

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
            for(MediaSrc m : mVideoPaths.get("area_"+i)){
                String md5 = fileToMD5(m.getUrlLocal());
                if(!md5.equals(m.getHashCode())){
                    File apkStorage = null;
                    File outputFile = null;
                    String newLocalUrl = redownloadMedia(outputFile, apkStorage, m.getUrl(),m.getTitle(), m.getExtension());
                    m.setUrlLocal(newLocalUrl);
                }
            }
//            playlistItemLists.put("area_"+i, playlistItemList); //co ve la ko can thiet

        }


        List<MediaView> mediaViews = new ArrayList<>();
        for (int i : areaIds) {
            boolean isMute = false;
            if(i != audioArea){
                isMute = true;
            }

            String area_name = "area_" + i;
            int areaId = getResources().getIdentifier("com.example.administrator.dssproject:id/" + area_name, null, null);
            final MediaView mediaView = view.findViewById(areaId);

            mediaView.setMediaSources(mPlaylistItemLists.get("area_"+i), mVideoPaths.get("area_"+i), isMute);
            mediaViews.add(mediaView);
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("myBox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("appStatus", Boolean.parseBoolean("true"));
        editor.commit();

        scheduleMediaShowing(mediaViews);
        scheduleMediaStop(mediaViews);
        scheduleMediaDownloader();

        return view;
    }

    private void scheduleMediaDownloader() {
//        long delayed = mScheduleInfo.endTime - System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30);
//        if (delayed < 0) {
//            delayed = 0;
//        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int BOXID = 0;
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("myBox", Context.MODE_PRIVATE);
                int boxId = sharedPreferences.getInt("boxid", BOXID);
                if(boxId == 0){
                    Intent intent = new Intent(mContext, BoxActivity.class);
                    startActivity(intent);
                }else{
                    boolean appStatus = true;
                    ApiData.getDataFromAPI(mContext, boxId, appStatus);
                }

                /*List<Box> boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                if (boxes.size() == 0) {
                    Intent intent = new Intent(mContext, BoxActivity.class);
                    startActivity(intent);
                } else {
                    boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                    int boxId = boxes.get(0).getBoxId();
                    ApiData.getDataFromAPI(mContext, boxId);
                }*/
            }
        },TimeUnit.MINUTES.toMillis(1));
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
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("myBox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("appStatus", Boolean.parseBoolean("false"));
                    editor.commit();
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
            byte [] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) { }
            }
        }
    }

    private static String convertHashToString(byte[] md5Bytes) {
        String returnVal = "";
        for (int i = 0; i < md5Bytes.length; i++) {
            returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
        }
        return returnVal;
    }


    File apkStorage = null;
    File outputFile = null;
    public static String redownloadMedia(File outputFile, File apkStorage, String downloadUrl, String downloadFileName, String downloadTailFileName ){
        String newUrl = "";
        String pathname = Environment.getExternalStorageDirectory() + "/"
                + Supporter.downloadDirectory;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                        + " " + c.getResponseMessage());
            }
            if (new CheckForSDCard().isExternalStorageWritable()) {
                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + Supporter.downloadDirectory);
            } else

            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            downloadFileName = downloadFileName + " " + timestamp;
            downloadFileName = downloadFileName.replace(" ","").trim();
            downloadFileName = downloadFileName.replace(":","").trim();
            downloadFileName = downloadFileName.replace("-","").trim();
            outputFile = new File(apkStorage, downloadFileName + downloadTailFileName);//Create Output file in Main File
            //Create New File if not present
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Log.e(TAG, "File Created");
            }
            Log.e("File download",outputFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
            InputStream is = c.getInputStream();//Get InputStream for connection
            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);//Write new file
            }
            //Close all connection after doing task
            fos.close();
            is.close();
        } catch (Exception e) {
            //Read exception if something went wrong
            e.printStackTrace();
            outputFile = null;
            Log.e(TAG, "Download Error Exception " + e.getMessage());
        }
        newUrl = pathname + "/" + downloadFileName + downloadTailFileName;
//        newUrl =  outputFile.getAbsolutePath();
        return newUrl;
    }

}
