package com.example.administrator.dssproject.SDCard;

import android.app.AlarmManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Time.ScheduleQueue;
import com.example.administrator.dssproject.Utils.Supporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "DownloadTask";
    private boolean downloadCompelete = false;
    private Context context;
    private List<MediaSrc> mediaSrcs;

    public DownloadTask(Context context) {
        this.context = context;
        this.mediaSrcs = mediaSrcs;
    }

    File apkStorage = null;
    File outputFile = null;

    @Override
    protected Void doInBackground(Void... arg0) {
        List<MediaSrc> listMedia = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        List<MediaSrc> downloadQueue = new ArrayList<MediaSrc>();
        for (int m = 0; m < listMedia.size(); m++) {
            if (listMedia.get(m).getUrlLocal().equals("F")) {
                try {
                    downloadQueue.add(listMedia.get(m));
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }

            }
        }
        for (int i = 0; i < downloadQueue.size(); i++) {
            String urlLocal = Supporter.savingDataToSDCard(context, outputFile, apkStorage, downloadQueue.get(i).getUrl(),
                    downloadQueue.get(i).getTitle(), downloadQueue.get(i).getExtension());
            downloadQueue.get(i).setUrlLocal(urlLocal);
        }

        for (int i = 0; i < downloadQueue.size(); i++) {
            MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrc(downloadQueue.get(i));
        }

        listMedia = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        ScheduleQueue.startSchedule(context, alarmManager);


        return null;
    }

    //Go to waiting fragment
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//            MainActivity.fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();

    }


    //go to assigned fragment by LayoutId
    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
//            Schedule schedule = MainActivity.myAppDatabase.scheduleDAO().getASchedule(ScheduleQueue.scheduleId);
//            int layoutId = schedule.getLayoutId();
//            switch (layoutId) {
//                case 1:
//                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Portriat9x16Fragment()).
//                            addToBackStack(null).commit();
//                    break;
//                case 29:
//                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new Landscape16x9Fragment()).
//                            addToBackStack(null).commit();
//                    break;
//                default:
//            }

    }
}
