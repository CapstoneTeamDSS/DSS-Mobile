package com.example.administrator.dssproject.API;

import android.content.Context;
import android.util.Log;

import com.example.administrator.dssproject.DataBase.Area;
import com.example.administrator.dssproject.DataBase.Layout;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Model.PlaylistItemDTO;
import com.example.administrator.dssproject.Model.ScenarioDTO;
import com.example.administrator.dssproject.Model.ScenarioItemDTO;
import com.example.administrator.dssproject.Model.ScheduleDTO;
import com.example.administrator.dssproject.SDCard.DownloadTask;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        boolean preCall = false;
        Call<ScheduleDTO> call = api.getScenarioByBoxId(boxId, preCall);
        call.enqueue(new Callback<ScheduleDTO>() {
            @Override
            public void onResponse(Call<ScheduleDTO> call, Response<ScheduleDTO> response) {
                ScheduleDTO scheduleAPI = response.body();

                long startTime = scheduleAPI.getStartTime();
                long endTime = scheduleAPI.getEndTime();
                ScenarioDTO scenarioDTO = scheduleAPI.getScenario();
                int scenarioId = scenarioDTO.getScenarioId();
                int layoutId = scenarioDTO.getLayoutId();

                //Demo
//                    long startTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10);
//                    long endTime = startTime + TimeUnit.SECONDS.toMillis(30);
                String title = scenarioDTO.getTitle();

                //Insert Layout to sqlite
                Layout layout = new Layout(layoutId);
                try {
                    boolean checkLayoutoId = checkDuplicateLayout(layoutId);
                    if (!checkLayoutoId) {
                        MainActivity.myAppDatabase.layoutDAO().addLayout(layout);
                    } else {
                        MainActivity.myAppDatabase.layoutDAO().updateLayout(layout);
                    }
                } catch (Exception e) {
                    Log.e(TAG + "Layout", e.toString());
                }

                //Insert Scenario to sqlite
                Scenario scenario = new Scenario(scenarioId, title, layoutId);
                try {
                    boolean checkScenarioId = checkDuplicateScenario(scenarioId, layoutId);
                    if (!checkScenarioId) {
                        MainActivity.myAppDatabase.scenarioDAO().addScenario(scenario);
                    } else {
                        MainActivity.myAppDatabase.scenarioDAO().updateScenario(scenario);
                    }
                } catch (Exception e) {
                    Log.e(TAG + "Scenario", e.toString());
                }
                for (int j = 0; j < scenarioDTO.getScenarioItems().size(); j++) {
                    ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(j);
                    //Insert Playlist to sqlite
                    int playlistId = scenarioItemDTO.getPlaylistId();
                    Playlist playlist = new Playlist(playlistId);
                    try {
                        boolean checkPlaylist = checkDuplicatePlaylist(playlistId);
                        if (!checkPlaylist) {
                            MainActivity.myAppDatabase.playlistDAO().addPlaylist(playlist);
                        } else {
                            MainActivity.myAppDatabase.playlistDAO().updatePlaylist(playlist);
                        }
                    } catch (Exception e) {
                        Log.e(TAG + "Playlist", e.toString());
                    }

                    int scenarioIdScen = scenarioItemDTO.getScenarioId();
                    int playlistIdScen = scenarioItemDTO.getPlaylistId();
                    int displayOrder = scenarioItemDTO.getDisplayOderPlaylist();
                    int areaId = scenarioItemDTO.getAreaId();
                    int visualTypeId = scenarioItemDTO.getVisualTypeId();

                    //Insert Area to sqlite
                    Area area = new Area(areaId, layoutId, visualTypeId);
                    try {
                        boolean checkAreaId = checkDuplicateArea(areaId, layoutId);
                        if (!checkAreaId) {
                            MainActivity.myAppDatabase.areaDAO().addArea(area);
                        } else {
                            MainActivity.myAppDatabase.areaDAO().updateArea(area);
                        }
                    } catch (Exception e) {
                        Log.e(TAG + "Area" , e.toString());
                    }

                    //Insert ScenarioItem to sqlite
                    ScenarioItem scenarioItem = new ScenarioItem(scenarioIdScen, playlistIdScen, displayOrder, layoutId, areaId);
                    try {
                        boolean checkScenarioItem = checkDuplicateScenarioItem(scenarioIdScen, playlistIdScen, layoutId, areaId);
                        if (!checkScenarioItem) {
                            MainActivity.myAppDatabase.scenarioItemDAO().addScenarioItem(scenarioItem);
                        } else {
                            MainActivity.myAppDatabase.scenarioItemDAO().updateScenarioItem(scenarioItem);
                        }
                    } catch (Exception e) {
                        Log.e(TAG + "ScenarioItem", e.toString());
                    }

                    for (int k = 0; k < scenarioDTO.getScenarioItems().get(j).getPlaylistItems().size(); k++) {
                        PlaylistItemDTO playlistItemDTO = scenarioDTO.getScenarioItems().get(j).getPlaylistItems().get(k);

                        //Insert MediaSrc in sqlite
                        int mediaSrcId = playlistItemDTO.getMediaSrcId();
                        String titleMedia = playlistItemDTO.getTitle();
                        int typeId = playlistItemDTO.getTypeId();
                        String url = playlistItemDTO.getUrlMedia();
                        String extension = playlistItemDTO.getExtensionMedia();
                        MediaSrc mediaSrc = new MediaSrc(mediaSrcId, titleMedia, typeId, url, extension, "F", "L");
                        String urlLocal = "";
                        try {
                            boolean checkMediaSrc = checkDuplicateMediaSrc(mediaSrcId);
                            if (!checkMediaSrc) {
                                MainActivity.myAppDatabase.mediaSrcDAO().addMediaSrc(mediaSrc);
                            } else {
                                if (checkAndGetDuplicateMediaSrc(mediaSrcId).getUrl().equals(url)) {
                                    urlLocal = checkAndGetDuplicateMediaSrc(mediaSrcId).getUrlLocal();
                                    MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrcExceptLocalUrl(mediaSrcId, titleMedia, typeId, url, extension, urlLocal, "L");
                                } else {
                                    urlLocal = "F";
                                    MainActivity.myAppDatabase.mediaSrcDAO().updateMediaSrcExceptLocalUrl(mediaSrcId, titleMedia, typeId, url, extension, urlLocal, "L");
                                }

                            }
                        } catch (Exception e) {
                            Log.e(TAG + "MediaSrc", e.toString());
                        }
                        //Insert PlaylistItem in sqlite
                        int playslistItemId = playlistItemDTO.getPlaylistItemId();
                        int mediaSrcIdit = playlistItemDTO.getMediaSrcId();
                        int playlistIdit = scenarioItemDTO.getPlaylistId();
                        int displayOrderPlaylistItem = playlistItemDTO.getDisplayOrder();
                        long duration = playlistItemDTO.getDuration();
                        PlaylistItem playlistItem = new PlaylistItem(playslistItemId, mediaSrcIdit, playlistIdit, displayOrderPlaylistItem, duration);
                        try {
                            boolean checkPlaylistItem = checkDuplicatePlaylistItem(playslistItemId);
                            if (!checkPlaylistItem) {
                                MainActivity.myAppDatabase.playlistItemDAO().addPlaylistItem(playlistItem);
                            } else {
                                MainActivity.myAppDatabase.playlistItemDAO().updatePlaylistItem(playlistItem);
                            }
                        } catch (Exception e) {
                            Log.e(TAG + "PlaylistItem", e.toString());
                        }
//                            MainActivity.myAppDatabase.playlistItemDAO().deleteAll();
//                            MainActivity.myAppDatabase.scenarioItemDAO().deleteAll();
//                            MainActivity.myAppDatabase.mediaSrcDAO().deleteAll();
//                            MainActivity.myAppDatabase.playlistDAO().deleteAll();
//                            MainActivity.myAppDatabase.scheduleDAO().deleteAll();
                    }
                }
                List<Scenario> scenarios = MainActivity.myAppDatabase.scenarioDAO().getScenarios();
                List<ScenarioItem> scenarioList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItems();
                List<Playlist> playlistList = MainActivity.myAppDatabase.playlistDAO().getPlaylists();
                List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItems();
                List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
                List<Layout> layoutList = MainActivity.myAppDatabase.layoutDAO().getLayout();
                List<Area> areaList = MainActivity.myAppDatabase.areaDAO().getArea();



                try {
                    new DownloadTask(context, scenarioId, startTime, endTime).execute();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(Call<ScheduleDTO> call, Throwable t) {
                Log.e(TAG, "" + call);
            }
        });
    }

    public static boolean checkDuplicateScenarioItem(int scenarioId, int playlistId, int layoutId, int areaId) {
        boolean check = false;
        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItems();
        for (int m = 0; m < scenarioItemList.size(); m++) {
            if (scenarioId == scenarioItemList.get(m).getScenarioId() && playlistId == scenarioItemList.get(m).getPlaylistId() && layoutId == scenarioItemList.get(m).getLayoutId() && areaId == scenarioItemList.get(m).getAreaId()) {
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicatePlaylist(int id) {
        boolean check = false;
        List<Playlist> playlistList = MainActivity.myAppDatabase.playlistDAO().getPlaylists();
        for (int m = 0; m < playlistList.size(); m++) {
            if (id == playlistList.get(m).getPlaylistId()) {
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicatePlaylistItem(int id) {
        boolean check = false;
        List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItems();
        for (int m = 0; m < playlistItemList.size(); m++) {
            if (id == playlistItemList.get(m).getPlaylistItemId()) {
                check = true;
            }
        }
        return check;
    }

    public static MediaSrc checkAndGetDuplicateMediaSrc(int id) {
        MediaSrc mediaSrc = null;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++) {
            if (id == mediaSrcList.get(m).getMediaSrcID()) {
                mediaSrc = mediaSrcList.get(m);
            }
        }
        return mediaSrc;
    }

    public static boolean checkDuplicateMediaSrc(int id) {
        boolean check = false;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++) {
            if (id == mediaSrcList.get(m).getMediaSrcID()) {
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicateScenario(int id, int layoutId) {
        boolean check = false;
        List<Scenario> scenariosList = MainActivity.myAppDatabase.scenarioDAO().getScenarios();
        for (int m = 0; m < scenariosList.size(); m++) {
            if (id == scenariosList.get(m).getScenarioId() && layoutId == scenariosList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicateLayout(int id) {
        boolean check = false;
        List<Layout> layoutList = MainActivity.myAppDatabase.layoutDAO().getLayout();
        for (int m = 0; m < layoutList.size(); m++) {
            if (id == layoutList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }

    public static boolean checkDuplicateArea(int id, int layoutId) {
        boolean check = false;
        List<Area> areaList = MainActivity.myAppDatabase.areaDAO().getArea();
        for (int m = 0; m < areaList.size(); m++) {
            if (id == areaList.get(m).getAreaId() && layoutId == areaList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }
}
