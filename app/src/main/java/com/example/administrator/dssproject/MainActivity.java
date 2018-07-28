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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.administrator.dssproject.API.ApiData;
import com.example.administrator.dssproject.DataBase.AppDatabase;
import com.example.administrator.dssproject.DataBase.Box;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.Fragment.Landscape2AreaFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public static final String ALARM_INTENT_FILTER_ACTION = "alarmIntentFilter";

    final static String TAG = "MainActivity";
    public static FragmentManager fragmentManager;
    public static AppDatabase myAppDatabase;
    private static int boxId;
    Calendar calendar;
    VideoView videoView;
    ImageView imageView;

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
//        scheduleApiCalls();
//        int boxId = 14;
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
                    ApiData.getDataFromAPI(MainActivity.this, boxId);
                }

            }
        }, 5000);

//        ApiData.getDataFromAPI(this, boxId);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.myAppDatabase.boxDAO().getABox();
                ApiData.getDataFromAPI(this, boxId);
            }
        }, 1800000);*/

//        videoView.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-21_16:01:28.1.mp4");
//        videoView.requestFocus();
//        videoView.start();
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

    private void scheduleApiCalls() {
        Handler handler = new Handler();
        for (int i = 0; i < 48; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<Box> boxList = MainActivity.myAppDatabase.boxDAO().getBox();
                    int boxId = boxList.get(0).boxId;
                    ApiData.getDataFromAPI(MainActivity.this, boxId);
                }
            },TimeUnit.MINUTES.toMillis(30));
        }
        scheduleApiCalls();
    }

    public static void getLayout(int layoutId, int scheduleId) {
        Fragment fragment;
        switch (layoutId) {
            case 10:
                fragment = Landscape2AreaFragment.newInstance(scheduleId);
                break;
            case 1:

                /*fragment = Landscape16x9Fragment.newInstance(scheduleId);
                break;*/
            default:
                throw new IllegalArgumentException("Unknown layout ID");
        }

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
