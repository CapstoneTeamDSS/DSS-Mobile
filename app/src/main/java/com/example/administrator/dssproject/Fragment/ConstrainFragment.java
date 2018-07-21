package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;
import com.example.administrator.dssproject.Time.ScheduleQueue;
import com.example.administrator.dssproject.Utils.Supporter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConstrainFragment extends Fragment {

    VideoView video1;
    VideoView video2;
    VideoView video3;
    VideoView video4;

    public ConstrainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_constrain, container, false);
        video1 = view.findViewById(R.id.areaView1);
        video2 = view.findViewById(R.id.areaView2);
        video3 = view.findViewById(R.id.areaView3);
        video4 = view.findViewById(R.id.areaView4);



        List<ScenarioItem> scenarioItemList35 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList36 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList37 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList38 = new ArrayList<ScenarioItem>();

        List<Playlist> playlistListOf35 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf36 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf37 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf38 = new ArrayList<Playlist>();

        List<PlaylistItem> playlistItemList35 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList36 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList37 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList38 = new ArrayList<PlaylistItem>();

        List<Schedule> scheduleList = ScheduleQueue.checkValidStartTime();
        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScehduleId(scheduleList.get(0).getScheduleId());
        for(int i = 0; i < scenarioItemList.size(); i++){
            if(scenarioItemList.get(i).getAreaId() == 35){
                scenarioItemList35 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(35);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList35);

            }else if(scenarioItemList.get(i).getAreaId() == 36){
                scenarioItemList36 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(36);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList36);


            }else if(scenarioItemList.get(i).getAreaId() == 37){
                scenarioItemList37 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(37);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList37);


            }else if(scenarioItemList.get(i).getAreaId() == 38){
                scenarioItemList38 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(38);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList38);

            }
        }
        for(int i = 0; i < scenarioItemList35.size(); i++){
            playlistListOf35 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList35.get(i).getPlaylistId());
        }
        for(int i = 0; i < scenarioItemList36.size(); i++){
            playlistListOf36 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList36.get(i).getPlaylistId());
        }
        for(int i = 0; i < scenarioItemList37.size(); i++){
            playlistListOf37 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList37.get(i).getPlaylistId());
        }
        for(int i = 0; i < scenarioItemList38.size(); i++){
            playlistListOf38 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList38.get(i).getPlaylistId());
        }

        for (int i = 0; i < playlistListOf35.size(); i++){
            playlistItemList35 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf35.get(i).getPlaylistId());
        }
        for (int i = 0; i < playlistListOf36.size(); i++){
            playlistItemList36 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf36.get(i).getPlaylistId());
        }
        for (int i = 0; i < playlistListOf37.size(); i++){
            playlistItemList37 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf37.get(i).getPlaylistId());
        }
        for (int i = 0; i < playlistListOf38.size(); i++){
            playlistItemList38 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf38.get(i).getPlaylistId());
        }

        //Play AreaId 35

        /*video1.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video1.requestFocus();
        video1.start();

        video2.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video2.requestFocus();
        video2.start();

        video3.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video3.requestFocus();
        video3.start();

        video4.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video4.requestFocus();
        video4.start();*/


//        video1.setVideoPath("/storage/emulated/0/DSSDownloadData/Cat_and_Bird_2018-07-19_12:13:39.087.mp4");
//        video1.start();
        return view;
    }

    public void playVideoFromSDCard(VideoView videoView, String srcPath){
        videoView.setVideoPath(srcPath);
        videoView.requestFocus();
        videoView.start();

    }

}
