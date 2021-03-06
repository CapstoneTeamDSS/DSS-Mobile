package com.example.administrator.dssproject.API;

import android.content.Context;
import android.util.Log;

import com.example.administrator.dssproject.DataBase.AppDatabase;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.DataBase.Schedule;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Model.PlaylistItemDTO;
import com.example.administrator.dssproject.Model.ScenarioItemDTO;
import com.example.administrator.dssproject.Model.ScheduleDTO;
import com.example.administrator.dssproject.SDCard.DownloadTask;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiData {

    final static String TAG = "Schedules";

    public static void getDataFromAPI(final Context context, int boxId) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Log.e(TAG, api.toString());
        List<Schedule> schedules;
        Call<List<ScheduleDTO>> call = api.getScheduleByBoxId(boxId);
        call.enqueue(new Callback<List<ScheduleDTO>>() {
            @Override
            public void onResponse(Call<List<ScheduleDTO>> call, Response<List<ScheduleDTO>> response) {
                List<ScheduleDTO> scheduleListAPI = response.body();
              for (int i = 0; i < scheduleListAPI.size(); i++) {
                    ScheduleDTO scheduleDTO = scheduleListAPI.get(i);
                    int scheduleId = scheduleDTO.getScheduleId();
                    int scenarioId = scheduleDTO.getScenarioId();
                    int layoutId = scheduleDTO.getLayoutId();
                    String title = scheduleDTO.getTitle();
                    String startTime = scheduleDTO.getStartTime();
                    String endTime = scheduleDTO.getEndTime();
                    int timesToPlay = scheduleDTO.getTimesToPlay();
                  //Insert Scenario to sqlite
                  Scenario scenario = new Scenario(scenarioId);
                  try{
                      boolean checkScenarioId = checkDuplicateScenario(scenarioId);
                      if(!checkScenarioId){
                          MainActivity.myAppDatabase.scenarioDAO().addScenario(scenario);
                      }else{
                          MainActivity.myAppDatabase.scenarioDAO().updateScenario(scenario);
                      }
                  }catch (Exception e){
                      Log.e(TAG, e.toString());
                  }

                  //Insert Schedule to sqlite
                  Schedule schedule = new Schedule(scheduleId, scenarioId, title, layoutId, startTime, endTime,timesToPlay);
                  try{
                      boolean checkScheduleId = checkDuplicateSchedule(scheduleId);
                      if (!checkScheduleId){
                          MainActivity.myAppDatabase.scheduleDAO().addSchedule(schedule);
                      }else{
                          MainActivity.myAppDatabase.scheduleDAO().updateSchedule(schedule);
                      }
                  }catch (Exception e){
                      Log.e(TAG, e.toString());
                  }

                    for (int j = 0; j < scheduleListAPI.get(i).getScenarioItems().size(); j++){
                        ScenarioItemDTO scenarioItemDTO = scheduleListAPI.get(i).getScenarioItems().get(j);

                        //Insert Playlist to sqlite
                        int playlistId = scenarioItemDTO.getPlaylistId();
                        Playlist playlist = new Playlist(playlistId);
                        try{
                            boolean checkPlaylist = checkDuplicatePlaylist(playlistId);
                            if (!checkPlaylist){
                                MainActivity.myAppDatabase.playlistDAO().addPlaylist(playlist);
                            }else{
                                MainActivity.myAppDatabase.playlistDAO().updatePlaylist(playlist);
                            }
                        }catch (Exception e){
                            Log.e(TAG, e.toString());
                        }

                        //Insert ScenarioItem to sqlite
                        int scenarioIdScen = scenarioItemDTO.getScenarioId();
                        int playlistIdScen = scenarioItemDTO.getPlaylistId();
                        int displayOrder = scenarioItemDTO.getDisplayOderPlaylist();
                        int areaId = scenarioItemDTO.getAreaId();
                        ScenarioItem scenarioItem = new ScenarioItem(scenarioIdScen, playlistIdScen, displayOrder, areaId);

                        try{
                            boolean checkScenarioItem = checkDuplicateScenarioItem(scenarioIdScen, playlistIdScen, areaId);
                            if (!checkScenarioItem){
                                MainActivity.myAppDatabase.scenarioItemDAO().addScenarioItem(scenarioItem);
                            }else {
                                MainActivity.myAppDatabase.scenarioItemDAO().updateScenarioItem(scenarioItem);
                            }
                        }catch (Exception e){
//                            Log.e(TAG, e.toString());
                        }

                        for (int k = 0; k < scheduleListAPI.get(i).getScenarioItems().get(j).getPlaylistItems().size(); k++){
                            PlaylistItemDTO playlistItemDTO = scheduleListAPI.get(i).getScenarioItems().get(j).getPlaylistItems().get(k);

                            //Insert MediaSrc in sqlite
                            int mediaSrcId = playlistItemDTO.getMediaSrcId();
                            String titleMedia = playlistItemDTO.getTitle();
                            int typeId = playlistItemDTO.getTypeId();
                            String url = playlistItemDTO.getUrlMedia();
                            String extension = playlistItemDTO.getExtensionMedia();
                            MediaSrc mediaSrc = new MediaSrc(mediaSrcId, titleMedia, typeId, url, extension, "F", "L");
                            String urlLocal = "";
                            try{
                                boolean checkMediaSrc = checkDuplicateMediaSrc(mediaSrcId);
                                if (!checkMediaSrc){
                                    MainActivity.myAppDatabase.mediaSrcDAO().addMediaSrc(mediaSrc);
                                }else {
                                    if(checkAndGetDuplicateMediaSrc(mediaSrcId).getUrl().equals(url)){
                                        urlLocal = checkAndGetDuplicateMediaSrc(mediaSrcId).getUrlLocal();
                                        MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrcExceptLocalUrl(mediaSrcId, titleMedia, typeId, url, extension, urlLocal, "L"  );
                                    }else{
                                        urlLocal = "F";
                                        MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrcExceptLocalUrl(mediaSrcId, titleMedia, typeId, url, extension, urlLocal, "L"  );
                                    }

                                }
                            }catch (Exception e){
                                Log.e(TAG, e.toString());
                            }


                            //Insert PlaylistItem in sqlite
                            int playslistItemId = playlistItemDTO.getPlaylistItemId();
                            int mediaSrcIdit = playlistItemDTO.getMediaSrcId();
                            int playlistIdit = scenarioItemDTO.getPlaylistId();
                            int displayOrderPlaylistItem = playlistItemDTO.getDisplayOrder();
                            String duration = playlistItemDTO.getDuration();
                            PlaylistItem playlistItem = new PlaylistItem(playslistItemId, mediaSrcIdit, playlistIdit, displayOrderPlaylistItem, duration);
                            try{
                                boolean checkPlaylistItem = checkDuplicatePlaylistItem(playslistItemId);
                                if (!checkPlaylistItem){
                                    MainActivity.myAppDatabase.playlistItemDAO().addPlaylistItem(playlistItem);
                                }else{
                                    MainActivity.myAppDatabase.playlistItemDAO().updatePlaylistItem(playlistItem);
                                }
                            }catch (Exception e){
                                Log.e(TAG, e.toString());
                            }

//                            MainActivity.myAppDatabase.playlistItemDAO().deleteAll();
//                            MainActivity.myAppDatabase.scenarioItemDAO().deleteAll();
//                            MainActivity.myAppDatabase.mediaSrcDAO().deleteAll();
//                            MainActivity.myAppDatabase.playlistDAO().deleteAll();
//                            MainActivity.myAppDatabase.scheduleDAO().deleteAll();

//                            MainActivity.myAppDatabase.scenarioItemDAO().addScenarioItem(scenarioItem);
//                            MainActivity.myAppDatabase.mediaSrcDAO().addMediaSrc(mediaSrc);
//                            MainActivity.myAppDatabase.playlistDAO().addPlaylist(playlist);
//                            MainActivity.myAppDatabase.scheduleDAO().addSchedule(schedule);
//                            MainActivity.myAppDatabase.playlistItemDAO().addPlaylistItem(playlistItem);


                        }
                    }

                }
                try {
                    new DownloadTask(context).execute();
                }catch (Exception e){
                    Log.e(TAG, e.toString());
                }

//
            }

            @Override
            public void onFailure(Call<List<ScheduleDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "" + call);
            }
        });
    }

    public static boolean checkDuplicateSchedule(int id){
        boolean check = false;
        List<Schedule> scheduleList = MainActivity.myAppDatabase.scheduleDAO().getSchedules();
        for (int m = 0; m < scheduleList.size(); m++){
            if(id == scheduleList.get(m).getScheduleId()){
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicateScenarioItem(int scenarioId, int playlistId, int areaId){
        boolean check = false;
        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItems();
        for (int m = 0; m < scenarioItemList.size(); m++){
            if(scenarioId == scenarioItemList.get(m).getScenarioId() && playlistId == scenarioItemList.get(m).getPlaylistId() && areaId == scenarioItemList.get(m).getAreaId()){
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicatePlaylist(int id){
        boolean check = false;
        List<Playlist> playlistList = MainActivity.myAppDatabase.playlistDAO().getPlaylists();
        for (int m = 0; m < playlistList.size(); m++){
            if(id == playlistList.get(m).getPlaylistId()){
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicatePlaylistItem(int id){
        boolean check = false;
        List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItems();
        for (int m = 0; m < playlistItemList.size(); m++){
            if(id == playlistItemList.get(m).getPlaylistItemId()){
                check = true;
            }
        }
        return check;
    }

    public static MediaSrc checkAndGetDuplicateMediaSrc(int id){
        MediaSrc mediaSrc = null;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++){
            if(id == mediaSrcList.get(m).getMediaSrcID()){
                mediaSrc = mediaSrcList.get(m);
            }
        }
        return mediaSrc;
    }

    public static boolean checkDuplicateMediaSrc(int id){
        boolean check = false;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++){
            if(id == mediaSrcList.get(m).getMediaSrcID()){
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicateScenario(int id){
        boolean check = false;
        List<Scenario> scenariosList = MainActivity.myAppDatabase.scenarioDAO().getScenarios();
        for (int m = 0; m < scenariosList.size(); m++){
            if(id == scenariosList.get(m).getScenarioId()){
                check = true;
            }
        }
        return check;
    }
}
