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
import com.example.administrator.dssproject.Utils.MediaView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConstrainFragment extends Fragment {

    private static final String ARG_SCENARIO_ID = "argScenarioId";

    private int mScheduleId;
    @NonNull
    private List<MediaSrc> mVideoPaths19 = new ArrayList<>();
    private List<MediaSrc> mVideoPaths20 = new ArrayList<>();


    MediaView video1;
    MediaView video2;
//    VideoView video3;
//    VideoView video4;

    public static ConstrainFragment newInstance(int scheduleId) {

        Bundle args = new Bundle();
        args.putInt(ARG_SCENARIO_ID, scheduleId);
        ConstrainFragment fragment = new ConstrainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mScheduleId = args.getInt(ARG_SCENARIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_constrain, container, false);

        video1 = view.findViewById(R.id.areaView1);
        video2 = view.findViewById(R.id.areaView2);
//        video3 = view.findViewById(R.id.areaView3);
//        video4 = view.findViewById(R.id.areaView4);


//        int scheduleId = 32;
        /*List<ScenarioItem> scenarioItemList35 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList36 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList37 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList38 = new ArrayList<ScenarioItem>();*/

        List<ScenarioItem> scenarioItemList19 = new ArrayList<ScenarioItem>();
        List<ScenarioItem> scenarioItemList20 = new ArrayList<ScenarioItem>();

        /*List<Playlist> playlistListOf35 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf36 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf37 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf38 = new ArrayList<Playlist>();*/

        List<Playlist> playlistListOf19 = new ArrayList<Playlist>();
        List<Playlist> playlistListOf20 = new ArrayList<Playlist>();

        /*List<PlaylistItem> playlistItemList35 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList36 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList37 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList38 = new ArrayList<PlaylistItem>();*/

        List<PlaylistItem> playlistItemList19 = new ArrayList<PlaylistItem>();
        List<PlaylistItem> playlistItemList20 = new ArrayList<PlaylistItem>();

        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemLIistByScenarioId(mScheduleId);
        for(int i = 0; i < scenarioItemList.size(); i++){
            if(scenarioItemList.get(i).getAreaId() == 19){
                scenarioItemList19 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(19);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList35);

            }else if(scenarioItemList.get(i).getAreaId() == 20){
                scenarioItemList20 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(20);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList36);


            }
            /*else if(scenarioItemList.get(i).getAreaId() == 37){

                scenarioItemList37 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(37);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList37);


            }else if(scenarioItemList.get(i).getAreaId() == 38){
                scenarioItemList38 = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItemListByAreaId(38);
//                Supporter.scenarioItemsSortByDisplayOrder(scenarioItemList38);

            }*/
        }
        for(int i = 0; i < scenarioItemList19.size(); i++){
            playlistListOf19 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList19.get(i).getPlaylistId());

        }
        for(int i = 0; i < scenarioItemList20.size(); i++){
            playlistListOf20 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList20.get(i).getPlaylistId());

        }
        /*for(int i = 0; i < scenarioItemList37.size(); i++){
            playlistListOf37 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList37.get(i).getPlaylistId());
        }
        for(int i = 0; i < scenarioItemList38.size(); i++){
            playlistListOf38 = MainActivity.myAppDatabase.playlistDAO().getPlaylistListByPlaylistId(scenarioItemList38.get(i).getPlaylistId());
        }*/
        List<String> localUrl19 = new ArrayList<String>();
        for (int i = 0; i < playlistListOf19.size(); i++){
            playlistItemList19 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf19.get(i).getPlaylistId());
            for(int j = 0; j < playlistItemList19.size(); j++){
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList19.get(j).getPlaylistItemId());
                mVideoPaths19.add(mediaSrc);
            }
        }

        List<String> localUrl20 = new ArrayList<String>();
        for (int i = 0; i < playlistListOf20.size(); i++){
            playlistItemList20 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf20.get(i).getPlaylistId());
            for(int j = 0; j < playlistItemList20.size(); j++){
                MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrcByPlaylistItemId(playlistItemList20.get(j).getPlaylistItemId());
                mVideoPaths20.add(mediaSrc);
            }
        }
        /*for (int i = 0; i < playlistListOf37.size(); i++){
            playlistItemList37 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf37.get(i).getPlaylistId());
        }
        for (int i = 0; i < playlistListOf38.size(); i++){
            playlistItemList38 = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItemByPlaylistId(playlistListOf38.get(i).getPlaylistId());
        }*/

        //Play AreaId 19
        MediaSrc mediaSrc = MainActivity.myAppDatabase.mediaSrcDAO().getAMediaSrc(playlistItemList19.get(1).getMediaSrcId());
        /*video1.setMediaSources(playlistItemList19, mVideoPaths19);
        video2.setMediaSources(playlistItemList20, mVideoPaths20);*/

        /*video3.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video3.requestFocus();
        video3.start();

        video4.setVideoPath("/storage/emulated/0/DSSDownloadData/Playing_Cat_2018-07-20_13:46:51.825.mp4");
        video4.requestFocus();
        video4.start();*/


//        video1.setVideoPath("/storage/emulated/0/DSSDownloadData/Cat_and_Bird_2018-07-19_12:13:39.087.mp4");
//        video1.start();
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
