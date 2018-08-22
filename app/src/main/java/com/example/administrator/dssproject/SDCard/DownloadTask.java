package com.example.administrator.dssproject.SDCard;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.Fragment.ControlFragment.ScheduleInfo;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Utils.Supporter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "DownloadTask";
    private WeakReference<Context> mContext;
    private List<MediaSrc> mSources;

    private final int mScenarioId;
    private final long mStartTime;
    private final long mEndTime;

    public DownloadTask(Context context, List<MediaSrc> sources, int scenarioId, long startTime, long endTime) {
        mContext = new WeakReference<>(context);
        mSources = sources;

        mStartTime = startTime;
        mEndTime = endTime;
        mScenarioId = scenarioId;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }

        List<MediaSrc> downloadQueue = getDownloadQueue();
        for (MediaSrc source : downloadQueue) {
            String urlLocal = Supporter.saveDataToSDCard(context, source.getUrl(), source.getTitle(), source.getExtension());
            source.setUrlLocal(urlLocal);

            MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrc(source);
        }

        int layoutId = MainActivity.myAppDatabase.scenarioDAO().getAScenario(mScenarioId).getLayoutId();
        ScheduleInfo info = new ScheduleInfo(mScenarioId, layoutId, mStartTime, mEndTime);
        MainActivity.replaceFragment(info);

        return null;
    }

    @NonNull
    private List<MediaSrc> getDownloadQueue() {
        List<MediaSrc> queue = new ArrayList<>();
        for (MediaSrc source : mSources) {
            if (source.getUrlLocal().equals("F")) {
                queue.add(source);
            }
        }
        return queue;
    }
}
