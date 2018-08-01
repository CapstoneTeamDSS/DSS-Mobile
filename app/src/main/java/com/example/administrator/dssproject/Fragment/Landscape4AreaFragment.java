package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;
import com.example.administrator.dssproject.Utils.ImageVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.dssproject.Time.ScheduleQueue.ARG_SCENARIO_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class Landscape4AreaFragment extends Fragment {

    private int mScenarioId;
    @NonNull
    private List<MediaSrc> mVideoPaths35 = new ArrayList<>();
    private List<MediaSrc> mVideoPaths36 = new ArrayList<>();
    private List<MediaSrc> mVideoPaths37 = new ArrayList<>();
    private List<MediaSrc> mVideoPaths38 = new ArrayList<>();

    private List<PlaylistItem> mPlaylistItemList35 = new ArrayList<>();
    private List<PlaylistItem> mPlaylistItemList36 = new ArrayList<>();
    private List<PlaylistItem> mPlaylistItemList37 = new ArrayList<>();
    private List<PlaylistItem> mPlaylistItemList38 = new ArrayList<>();

    ImageVideoView video1;
    ImageVideoView video2;
    ImageVideoView video3;
    ImageVideoView video4;



    public static Landscape4AreaFragment newInstance(int scenarioId){
        Bundle args = new Bundle();
        args.putInt(ARG_SCENARIO_ID, scenarioId);
        Landscape4AreaFragment fragment = new Landscape4AreaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mScenarioId = args.getInt(ARG_SCENARIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_landscape4_area, container, false);

        video1 = view.findViewById(R.id.areaView1);
        video2 = view.findViewById(R.id.areaView2);
        video3 = view.findViewById(R.id.areaView3);
        video4 = view.findViewById(R.id.areaView4);

        Scenario scenario = MainActivity.myAppDatabase.scenarioDAO().getAScenario(mScenarioId);

        List<ScenarioItem> scenarioItemList35 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList36 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList37 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList38 = new ArrayList<ScenarioItem>();

        List<Integer> playlistListOf35 = new ArrayList<>();
        List<Integer> playlistListOf36 = new ArrayList<>();
        List<Integer> playlistListOf37 = new ArrayList<>();
        List<Integer> playlistListOf38 = new ArrayList<>();

        List<PlaylistItem> playlistItemList35 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList36 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList37 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList38 = new ArrayList<PlaylistItem>();

        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScenarioId(mScenarioId);
        for (int i = 0; i < scenarioItemList.size(); i++) {
            if (scenarioItemList.get(i).getAreaId() == 35) {
                scenarioItemList35 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(35);
            } else if (scenarioItemList.get(i).getAreaId() == 36) {
                scenarioItemList36 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(36);
            } else if (scenarioItemList.get(i).getAreaId() == 37) {
                scenarioItemList37 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(37);
            } else if (scenarioItemList.get(i).getAreaId() == 38) {
                scenarioItemList38 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(38);
            }
        }

        for (int i = 0; i < scenarioItemList35.size(); i++) {
            playlistListOf35.add(scenarioItemList35.get(i).getPlaylistId());
        }
        for (int i = 0; i < scenarioItemList36.size(); i++) {
            playlistListOf36.add(scenarioItemList36.get(i).getPlaylistId());
        }
        for (int i = 0; i < scenarioItemList37.size(); i++) {
            playlistListOf37.add(scenarioItemList37.get(i).getPlaylistId());
        }
        for (int i = 0; i < scenarioItemList38.size(); i++) {
            playlistListOf38.add(scenarioItemList38.get(i).getPlaylistId());
        }

        for (int i = 0; i < playlistListOf35.size(); i++) {
            playlistItemList35 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf35.get(i));
            for (int j = 0; j < playlistItemList35.size(); j++) {
                mPlaylistItemList35.add(playlistItemList35.get(j));
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList35.get(j).getPlaylistItemId());
                mVideoPaths35.add(mediaSrc);
            }
        }
        for (int i = 0; i < playlistListOf36.size(); i++) {
            playlistItemList36 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf36.get(i));
            for (int j = 0; j < playlistItemList36.size(); j++) {
                mPlaylistItemList36.add(playlistItemList36.get(j));
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList36.get(j).getPlaylistItemId());
                mVideoPaths36.add(mediaSrc);
            }
        }
        for (int i = 0; i < playlistListOf37.size(); i++) {
            playlistItemList37 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf37.get(i));
            for (int j = 0; j < playlistItemList37.size(); j++) {
                mPlaylistItemList37.add(playlistItemList37.get(j));
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList37.get(j).getPlaylistItemId());
                mVideoPaths37.add(mediaSrc);
            }
        }
        for (int i = 0; i < playlistListOf38.size(); i++) {
            playlistItemList38 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf38.get(i));
            for (int j = 0; j < playlistItemList38.size(); j++) {
                mPlaylistItemList38.add(playlistItemList38.get(j));
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList38.get(j).getPlaylistItemId());
                mVideoPaths38.add(mediaSrc);
            }
        }

        video1.setMediaSources(mPlaylistItemList35, mVideoPaths35);
        video2.setMediaSources(mPlaylistItemList36, mVideoPaths36);
        video3.setMediaSources(mPlaylistItemList37, mVideoPaths37);
        video4.setMediaSources(mPlaylistItemList38, mVideoPaths38);


        return view;
    }

}
