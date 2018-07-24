package com.example.administrator.dssproject.Fragment;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.R;
import com.example.administrator.dssproject.Utils.ImageVideoView;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.dssproject.Time.ScheduleQueue.ARG_SCHEDULE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class Landscape2SideFragment extends Fragment {


    private int mScheduleId;
    @NonNull
    private List<MediaSrc> mVideoPaths19 = new ArrayList<>();
    private List<MediaSrc> mVideoPaths20 = new ArrayList<>();


    ImageVideoView video1;
    ImageVideoView video2;

    public static Landscape2SideFragment newInstance(int scheduleId) {
        Bundle args = new Bundle();
        args.putInt(ARG_SCHEDULE_ID, scheduleId);
        Landscape2SideFragment fragment = new Landscape2SideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mScheduleId = args.getInt(ARG_SCHEDULE_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_landscape2_side, container, false);
        video1 = view.findViewById(R.id.areaView1);
        video2 = view.findViewById(R.id.areaView2);

        List<ScenarioItem> scenarioItemList19 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList20 = new ArrayList<ScenarioItem>();

        List<Playlist> playlistListOf19 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf20 = new ArrayList<Playlist>();

        List<PlaylistItem> playlistItemList19 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList20 = new ArrayList<PlaylistItem>();

        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScehduleId(mScheduleId);
        for(int i = 0; i < scenarioItemList.size(); i++){
            if(scenarioItemList.get(i).getAreaId() == 19){
                scenarioItemList19 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(19);

            }else if(scenarioItemList.get(i).getAreaId() == 20){
                scenarioItemList20 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(20);


            }

        }
        for(int i = 0; i < scenarioItemList19.size(); i++){
            playlistListOf19 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList19.get(i).getPlaylistId());

        }
        for(int i = 0; i < scenarioItemList20.size(); i++){
            playlistListOf20 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList20.get(i).getPlaylistId());

        }

        for (int i = 0; i < playlistListOf19.size(); i++){
            playlistItemList19 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf19.get(i).getPlaylistId());
            for(int j = 0; j < playlistItemList19.size(); j++){
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList19.get(j).getPlaylistItemId());
                mVideoPaths19.add(mediaSrc);
            }
        }

        for (int i = 0; i < playlistListOf20.size(); i++){
            playlistItemList20 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf20.get(i).getPlaylistId());
            for(int j = 0; j < playlistItemList20.size(); j++){
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList20.get(j).getPlaylistItemId());
                mVideoPaths20.add(mediaSrc);
            }
        }

        video1.setMediaSources(playlistItemList19, mVideoPaths19);
        video2.setMediaSources(playlistItemList20, mVideoPaths20);

        return view;
    }




    private class OnVideoCompletionListener implements MediaPlayer.OnCompletionListener {

        private final VideoView mVideoView;
        @NonNull
        private List<String> mPaths;
        private int mVideoIndex = 0;

        OnVideoCompletionListener(VideoView view, @NonNull List<String> paths) {
            mVideoView = view;
            mPaths = paths;

            mVideoView.setVideoPath(mPaths.get(mVideoIndex++));
            mVideoView.requestFocus();
            mVideoView.start();
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (mVideoIndex < mPaths.size()) {
                Context context = mVideoView.getContext();
                Toast.makeText(context, "Video has just finished", Toast.LENGTH_SHORT).show();
                mVideoView.setVideoPath(mPaths.get(mVideoIndex));
                mVideoView.start();
                mVideoIndex++;
            }
        }
    }

}
