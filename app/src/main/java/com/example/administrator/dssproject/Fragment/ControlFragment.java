package com.example.administrator.dssproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
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
import java.util.HashMap;
import java.util.List;

import static com.example.administrator.dssproject.Time.ScheduleQueue.ARG_SCENARIO_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends Fragment {

    private int mScenarioId;
    private int mLayoutId;

    @NonNull
    private HashMap<String, List<MediaSrc>> mVideoPaths = new HashMap<String, List<MediaSrc>>();
    private HashMap<String, List<PlaylistItem>> mPlaylistItemLists = new HashMap<String, List<PlaylistItem>>();

    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance(int scenarioId, int layoutId) {
        Bundle args = new Bundle();
        args.putInt(ARG_SCENARIO_ID, scenarioId);
        args.putInt("LAYOUT_ID", layoutId);
        ControlFragment fragment = new ControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mScenarioId = args.getInt(ARG_SCENARIO_ID);
            mLayoutId = args.getInt("LAYOUT_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String layout_name = "layout_" + mLayoutId;
        int layoutId = getResources().getIdentifier("com.example.administrator.dssproject:layout/" + layout_name, null, null);
        // Inflate the layout for this fragment
        View view = inflater.inflate(layoutId, container, false);
        HashMap<String,ImageVideoView> videos = new HashMap<String, ImageVideoView>(); //Khoi tao imageVideoView
        int[] areaIds = {35, 36, 37, 38}; //Get areaId list TODO
        Scenario scenario = MainActivity.myAppDatabase.scenarioDAO().getAScenario(mScenarioId);
        HashMap<String, List<ScenarioItem>> scenarioItemLists = new HashMap<String, List<ScenarioItem>>();
        for (int i : areaIds) {
            scenarioItemLists.put("area_" + i, new ArrayList<ScenarioItem>());
        }
        HashMap<String, List<Integer>> playlistLists = new HashMap<String, List<Integer>>();
        HashMap<String, List<PlaylistItem>> playlistItemLists = new HashMap<String, List<PlaylistItem>>();
        List<ScenarioItem> scenarioItemListDB = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScenarioId(mScenarioId);
        for (int i = 0; i < scenarioItemListDB.size(); i++) {
            int areaId = scenarioItemListDB.get(i).getAreaId();
            scenarioItemLists.put("area_" + areaId, MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(areaId));
        }
        for (int i : areaIds) {
            List<ScenarioItem> scenarioItemList = scenarioItemLists.get("area_" + i);
            List<Integer> playlistList = new ArrayList<Integer>();
            for (ScenarioItem scenarioItem : scenarioItemList) {
                playlistList.add(scenarioItem.getPlaylistId());
            }
            playlistLists.put("area_" + i, playlistList);
        }
        for (int i : areaIds) {
            List<Integer> playlistList = playlistLists.get("area_" + i);
            List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
            List<MediaSrc> mediaSrcList = new ArrayList<MediaSrc>();
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
        for (int i : areaIds) {
            String area_name = "area_" + i;
            int areaId = getResources().getIdentifier("com.example.administrator.dssproject:id/" + area_name, null, null);
            ImageVideoView video = view.findViewById(areaId);
            video.setMediaSources(mPlaylistItemLists.get("area_"+i), mVideoPaths.get("area_"+i));
            videos.put("area_"+i, video);
        }
        return view;
    }
}
