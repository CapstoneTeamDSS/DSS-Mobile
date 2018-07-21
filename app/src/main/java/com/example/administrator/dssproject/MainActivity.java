package com.example.administrator.dssproject;

import android.Manifest;
import android.app.AlarmManager;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.DataBase.AppDatabase;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.Fragment.BoxFragment;
import com.example.administrator.dssproject.Fragment.ConstrainFragment;
import com.example.administrator.dssproject.Fragment.Landscape16x9Fragment;
import com.example.administrator.dssproject.Fragment.Portriat9x16Fragment;
import com.example.administrator.dssproject.SDCard.DownloadTask;
import com.example.administrator.dssproject.Time.ScheduleQueue;
import com.example.administrator.dssproject.Utils.Supporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    public static FragmentManager fragmentManager;
    public static AppDatabase myAppDatabase;
    Calendar calendar;
    VideoView videoView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        shouldAskPermissionWrite();
        shouldAskPermissionRead();
        int boxId = 14;

        ApiData.getDataFromAPI(this, boxId);
        /*videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-21_16:01:28.1.mp4");
        videoView.requestFocus();
        videoView.start();*/
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        fragmentManager = getSupportFragmentManager();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "dssdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        //Input Boxid
       /* List<Box> boxList = MainActivity.myAppDatabase.boxDAO().getBox();
        if (boxList.size() == 0){
            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new BoxFragment()).
                    addToBackStack(null).commit();
        }*/


//        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScehduleId(30);
//        List<ScenarioItem> scenarioItemList2 = Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList);
//        List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(33);
//        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaByScheduleId(30);

        //Get schedule to use
        /*List<Schedule> scheduleList = ScheduleQueue.checkValidStartTime();

        List<Schedule> scheduleListNew = ScheduleQueue.scheduleListOrderStartTime(scheduleList);
        for (Schedule item : scheduleListNew) {
            Log.e(TAG, item.getScheduleId() + " "+
                    item.getLayoutId()+ " "+
                    item.getStartTime()+ " "+
                    item.getEndTime()+ " "+
                    item.getTitle() + " "+
                    item.getTimesToPlay()+ " \n");
        }*/
//        //Demo play Video
//        getLayout(scheduleListNew.get(0).getLayoutId());


        //Change LocalUrl//////////////////////////////
        /*List<MediaSrc> mediaSrcList =  MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for(int i = 0; i < mediaSrcList.size(); i++){
            mediaSrcList.get(i).setUrl("https://cdn.filestackcontent.com/Aa7YKYzJQ22PikjDFtPM");
            Log.e(TAG, mediaSrcList.get(i).getUrl() + " " +
                    mediaSrcList.get(i).getMediaSrcID());
            MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrc(mediaSrcList.get(i));
        }*/


        /*List<MediaSrc> mediaSrcList =  MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (MediaSrc item : mediaSrcList) {
            Log.e("MediaSrcList", item.getUrl() + " "+
                    item.getTypeID()+ " "+
                    item.getMediaSrcID()+ " "+
                    item.getTitle() + " "+
                    item.getExtension() + " "+
                    item.getUrlLocal()+ "\n");
        }*/

        //Download Task
//        try {
//            new DownloadTask(this, alarmManager);
//        }catch (Exception e){
//            Log.e(TAG, e.toString());
//        }


        //Order StartTime Schedule
        /*List<Schedule> scheduleList = ScheduleQueue.checkValidStartTime();

        List<Schedule> scheduleListNew = ScheduleQueue.scheduleListOrderStartTime(scheduleList);
        for (Schedule item : scheduleListNew) {
            Log.e(TAG, item.getScheduleId() + " "+
                    item.getStartTime()+ " "+
                    item.getEndTime()+ " "+
                    item.getTitle() + " "+
                    item.getTimesToPlay()+ " \n");
        }*/


//        Toast.makeText(this, "SSSSSSSS", Toast.LENGTH_SHORT).show();

        /*if(findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeLayout()).commit();

        }*/


       /* List<MediaSrc> mediaSrcList2 = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (MediaSrc item : mediaSrcList2) {
            Log.e("MediaSrc", item.getUrl() + " " +
                    item.getTypeID() + " " +
                    item.getMediaSrcID() + " " +
                    item.getTitle() + " " +
                    item.getExtension() + " " +
                    item.getUrlLocal() + "\n");
        }*/
    }


    //**************************************************************//
    //**********************************************Supporter****************************//


    public static void getLayout(int layoutId, int scheduleId) {
        Fragment fragment;
        switch (layoutId) {
            case 10:
                fragment = ConstrainFragment.newInstance(scheduleId);
                break;
            case 1:

                /*fragment = Landscape16x9Fragment.newInstance(scheduleId);
                break;*/
            default:
                throw new IllegalArgumentException("Unknown layout ID");
        }

        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }


    public void shouldAskPermissionWrite() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        } else {

        }
    }

    public void shouldAskPermissionRead() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);
            }
        } else {

        }
    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public List<MediaSrc> getAllMediaSrc() {
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();

        return mediaSrcList;
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        private static final int INVALID_SCHEDULE_ID = -1;

        public MyBroadcastReceiver() {}

        @Override
        public void onReceive(Context context, Intent intent) {
            int scheduleId = intent.getIntExtra(ScheduleQueue.ARG_SCHEDULE_ID, INVALID_SCHEDULE_ID);
            if (scheduleId == INVALID_SCHEDULE_ID) {
                return;
            }

            //Hide ImageView
            findViewById(R.id.iv_placeholder).setVisibility(View.GONE);

            Schedule schedule = MainActivity.myAppDatabase.scheduleDAO().getASchedule(scheduleId);
            int layoutId = schedule.getLayoutId();
            MainActivity.getLayout(layoutId, scheduleId);

//        Log.e("LayoutId: ", layoutID);
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        }
    }
}

//List all Schedule
        /*List<Schedule> scheduleList = MainActivity.myAppDatabase.scheduleDAO().getSchedules();
        for (Schedule item: scheduleList) {
            Log.e(TAG, item.getTitle() + " "+
                    item.getScheduleId()+ " "+
                    item.getStartTime()+ " "+
                    item.getEndTime() + " "+
                    item.getTimesToPlay());
        }*/

        /*

        List<Scenario> scenarios = MainActivity.myAppDatabase.scenarioDAO().getScenarios();
        for (Scenario item : scenarios) {
            Log.e("Scenario", item.getScenarioId() + "\n");

        }

        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItems();
        for (ScenarioItem item : scenarioItemList) {
            Log.e("ScenarioItem", item.getPlaylistId() + " "+
                    item.getDisplayOrder()+ " "+
                    item.getAreaId()+ " "+
                    item.getScenarioId() + "\n");

        }

        List<Playlist> playlistList = MainActivity.myAppDatabase.playlistDAO().getPlaylists();
        for (Playlist item : playlistList) {
            Log.e("Playlist", item.getPlaylistId() + "\n");

        }

        List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItems();
        for (PlaylistItem item : playlistItemList) {
            Log.e("PlaylistItem", item.getPlaylistItemId() + " "+
                    item.getDisplayOrder()+ " "+
                    item.getPlaylistId()+ " "+
                    item.getMediaSrcId()+ " "+
                    item.getDuration() + "\n");

        }

        List<MediaSrc> mediaSrcList2 = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (MediaSrc item : mediaSrcList2) {
            Log.e("MediaSrc", item.getUrl() + " "+
                    item.getTypeID()+ " "+
                    item.getMediaSrcID()+ " "+
                    item.getTitle() + " "+
                    item.getExtension() + " "+
                    item.getUrlLocal() + "\n");
        }*/
