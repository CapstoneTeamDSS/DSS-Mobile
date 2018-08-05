package com.example.administrator.dssproject;

import android.Manifest;
import android.app.AlarmManager;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.DataBase.AppDatabase;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.Fragment.ControlFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    public static final String ALARM_INTENT_FILTER_ACTION = "alarmIntentFilter";

    final static String TAG = "MainActivity";
    public static FragmentManager fragmentManager;
    public static AppDatabase myAppDatabase;
    private static int boxId;

    private final BroadcastReceiver mAlarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.iv_placeholder).setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        shouldAskPermissionWrite();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Box> boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                if (boxes.size() == 0) {
                    Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                    startActivity(intent);
                } else {
                    boxes = MainActivity.myAppDatabase.boxDAO().getBox();
                    int boxId = boxes.get(0).getBoxId();
                    boolean preCall = false;
                    ApiData.getDataFromAPI(MainActivity.this, boxId);
                }
            }
        }, 5000);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        fragmentManager = getSupportFragmentManager();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "dssdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mAlarmReceiver, new IntentFilter(ALARM_INTENT_FILTER_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mAlarmReceiver);
    }

    //**************************************************************//
    //**********************************************Supporter****************************//

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }


    public static void getLayout(ControlFragment.ScheduleInfo scheduleInfo) {
        Fragment fragment = ControlFragment.newInstance(scheduleInfo);
        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commitAllowingStateLoss();
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
