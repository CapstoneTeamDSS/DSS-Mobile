package com.example.administrator.dssproject.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.dssproject.DataBase.Area;
import com.example.administrator.dssproject.DataBase.Layout;
import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.Playlist;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.Scenario;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.Model.PlaylistDTO;
import com.example.administrator.dssproject.Model.PlaylistItemDTO;
import com.example.administrator.dssproject.Model.ScenarioDTO;
import com.example.administrator.dssproject.Model.ScenarioItemDTO;
import com.example.administrator.dssproject.Model.ScheduleDTO;
import com.example.administrator.dssproject.SDCard.DownloadTask;
import com.example.administrator.dssproject.Utils.Supporter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiData {

    final static String TAG = "Schedules";
    private static int mBoxId;
    private static Context mContext;
    private static boolean mCheckAppStatus;

    public static void getDataFromAPI(final Context context, int boxId, boolean checkAppStatus) {
        mBoxId = boxId;
        mContext = context;
        mCheckAppStatus = checkAppStatus;
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);
        Log.e(TAG, api.toString());

        Call<ScheduleDTO> call = api.getScenarioByBoxId(mBoxId);
        call.enqueue(new Callback<ScheduleDTO>() {
            @Override
            public void onResponse(Call<ScheduleDTO> call, Response<ScheduleDTO> response) {
                ScheduleDTO scheduleAPI = response.body();
                if (scheduleAPI != null) {
                    extractMedia(scheduleAPI, mCheckAppStatus);
                } else {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDataFromAPI(context, mBoxId, mCheckAppStatus);
                        }
                    }, TimeUnit.MINUTES.toMillis(1));
                }
            }

            @Override
            public void onFailure(Call<ScheduleDTO> call, Throwable t) {
                Log.e(TAG, "" + call);
            }
        });
    }

    private static void extractMedia(ScheduleDTO scheduleAPI, final boolean checkAppStatus) {
        long startTime = scheduleAPI.getStartTime();
        long endTime = scheduleAPI.getEndTime();
        ScenarioDTO scenarioDTO = scheduleAPI.getScenario();
        boolean checkUpdate = getAPIData(scenarioDTO, checkAppStatus);
        if (!checkUpdate) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDataFromAPI(mContext, mBoxId, checkAppStatus);
                }
            }, TimeUnit.MINUTES.toMillis(1));
        } else {
            try {
                new DownloadTask(mContext, scenarioDTO.getScenarioId(), startTime, endTime).execute();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private static boolean checkDuplicateScenarioItem(int scenarioId, int playlistId, int layoutId, int areaId) {
        boolean check = false;
        List<ScenarioItem> scenarioItemList = MainActivity.myAppDatabase.scenarioItemDAO().getScenarioItems();
        for (int m = 0; m < scenarioItemList.size(); m++) {
            if (scenarioId == scenarioItemList.get(m).getScenarioId() && playlistId == scenarioItemList.get(m).getPlaylistId() && layoutId == scenarioItemList.get(m).getLayoutId() && areaId == scenarioItemList.get(m).getAreaId()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean checkDuplicatePlaylist(int id) {
        boolean check = false;
        List<Playlist> playlistList = MainActivity.myAppDatabase.playlistDAO().getPlaylists();
        for (int m = 0; m < playlistList.size(); m++) {
            if (id == playlistList.get(m).getPlaylistId()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean checkDuplicatePlaylistItem(int id) {
        boolean check = false;
        List<PlaylistItem> playlistItemList = MainActivity.myAppDatabase.playlistItemDAO().getPlaylistItems();
        for (int m = 0; m < playlistItemList.size(); m++) {
            if (id == playlistItemList.get(m).getPlaylistItemId()) {
                check = true;
            }
        }
        return check;
    }

    private static MediaSrc checkAndGetDuplicateMediaSrc(int id) {
        MediaSrc mediaSrc = null;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++) {
            if (id == mediaSrcList.get(m).getMediaSrcID()) {
                mediaSrc = mediaSrcList.get(m);
            }
        }
        return mediaSrc;
    }

    private static boolean checkDuplicateMediaSrc(int id) {
        boolean check = false;
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        for (int m = 0; m < mediaSrcList.size(); m++) {
            if (id == mediaSrcList.get(m).getMediaSrcID()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean checkDuplicateScenario(int id, int layoutId) {
        boolean check = false;
        List<Scenario> scenariosList = MainActivity.myAppDatabase.scenarioDAO().getScenarios();
        for (int m = 0; m < scenariosList.size(); m++) {
            if (id == scenariosList.get(m).getScenarioId() && layoutId == scenariosList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean checkDuplicateLayout(int id) {
        boolean check = false;
        List<Layout> layoutList = MainActivity.myAppDatabase.layoutDAO().getLayout();
        for (int m = 0; m < layoutList.size(); m++) {
            if (id == layoutList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean checkDuplicateArea(int id, int layoutId) {
        boolean check = false;
        List<Area> areaList = MainActivity.myAppDatabase.areaDAO().getArea();
        for (int m = 0; m < areaList.size(); m++) {
            if (id == areaList.get(m).getAreaId() && layoutId == areaList.get(m).getLayoutId()) {
                check = true;
            }
        }
        return check;
    }

    private static boolean getAPIData(ScenarioDTO scenarioDTO, boolean checkAppStatus) { //return true: downnload va chieu lai; return false: chieu tiep
        boolean updateCheck = false;
        int scenarioId = scenarioDTO.getScenarioId();
        long scenarioUpdateDateTime = scenarioDTO.getScenUpdate();
        int layoutId = scenarioDTO.getLayoutId();
        String title = scenarioDTO.getTitle();
        int audioArea = scenarioDTO.getAudioArea();
        long addTime = System.currentTimeMillis();

        //Insert Layout to sqlite
        Layout layout = new Layout(layoutId);
        insertLayout(layout, layoutId);

        //Scenario business part
        Scenario scenario = new Scenario(scenarioId, title, scenarioUpdateDateTime, layoutId, audioArea, addTime);
        try {
            boolean checkScenarioDuplicated = checkDuplicateScenario(scenarioId, layoutId);
            Scenario currentShowingScenario = MainActivity.myAppDatabase.scenarioDAO().getLastScenario();
            if (!checkScenarioDuplicated) {
                MainActivity.myAppDatabase.scenarioDAO().addScenario(scenario);
                insertDataFromScenarioDTO(scenarioDTO);
                return true;
            } else {
                Scenario existedScenario = null;
                if (currentShowingScenario.getScenarioId() == scenarioId) {//xac dinh Scenario vua nhan co giong cai hien tai ko
                    existedScenario = currentShowingScenario;

                } else {
                    existedScenario = MainActivity.myAppDatabase.scenarioDAO().getAScenario(scenarioId);
                    updateForThisScenario(scenarioDTO);
                    updateCheck = true;
                }
                if (scenarioUpdateDateTime != existedScenario.getScenarioUpdateDateTime()) {//xac dinh time Update co thay doi ko
                    if (currentShowingScenario.getScenarioId() == scenarioId) {
                        updateCheck = true;
                    }
                    updateScenario(scenario, scenarioDTO);
                } else {//update area khi có update playlist
                    List<Integer> playlistIdList = MainActivity.myAppDatabase.scenarioItemDAO().getPlaylistsOfScenarioByScenarioId(scenarioId);
                    List<PlaylistDTO> playlistDTOList = getPlaylistDTOList(scenarioDTO);
//                    List<ScenarioItemDTO> apiPlaylistList = scenarioDTO.getScenarioItems();
                    for (int playlistId : playlistIdList) {
                        Playlist playlist = MainActivity.myAppDatabase.playlistDAO().getAPlaylist(playlistId);
                        for (PlaylistDTO apiPlaylist : playlistDTOList) {
                            if(playlist.getPlaylistId() == apiPlaylist.getPlaylistId()){
                                if (apiPlaylist.getPlaylistUpdateDateTime() != playlist.getPlaylistUpdate()) {
                                    if (currentShowingScenario.getScenarioId() == scenarioId) {
                                        updateCheck = true;
                                    }
                                    long playlistUpdateDateTime = apiPlaylist.getPlaylistUpdateDateTime();
                                    Playlist newPlaylist = new Playlist(playlistId, playlistUpdateDateTime);
                                    updatePlaylist(scenarioDTO, newPlaylist);
                                }
                            }
                        }
                    }
                }
            }
            if (!checkAppStatus) {

                return true;
            }
        } catch (Exception e) {
            Log.e(TAG + "Scenario", e.toString());
        }
//        insertDataFromScenarioDTO(scenarioDTO);

        return updateCheck;
    }

    private static void updateForThisScenario(ScenarioDTO scenarioDTO) {
        MainActivity.myAppDatabase.scenarioItemDAO().deleteScenarioItemByScenarioId(scenarioDTO.getScenarioId());
        insertNewScenarioItems(scenarioDTO);
    }

    private static void insertLayout(Layout layout, int layoutId) {
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
    }

    private static void insertArea(Area area, int areaId, int layoutId) {
        try {
            boolean checkAreaId = checkDuplicateArea(areaId, layoutId);
            if (!checkAreaId) {
                MainActivity.myAppDatabase.areaDAO().addArea(area);
            } else {
                MainActivity.myAppDatabase.areaDAO().updateArea(area);
            }
        } catch (Exception e) {
            Log.e(TAG + "Area", e.toString());
        }
    }

    private static void insertPlaylist(Playlist playlist, int playlistId) {
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
    }

    private static void insertPlaylistItem(PlaylistItem playlistItem, int playlistItemId) {
        try {
            boolean checkPlaylistItem = checkDuplicatePlaylistItem(playlistItemId);
            if (!checkPlaylistItem) {
                MainActivity.myAppDatabase.playlistItemDAO().addPlaylistItem(playlistItem);
            } else {
                MainActivity.myAppDatabase.playlistItemDAO().updatePlaylistItem(playlistItem);
            }
        } catch (Exception e) {
            Log.e(TAG + "PlaylistItem", e.toString());
        }
    }

    private static void insertScenarioItem(ScenarioItem scenarioItem, int scenarioId, int playlistId, int layoutId, int areaId) {
        try {
            boolean checkScenarioItem = checkDuplicateScenarioItem(scenarioId, playlistId, layoutId, areaId);
            if (!checkScenarioItem) {
                MainActivity.myAppDatabase.scenarioItemDAO().addScenarioItem(scenarioItem);
            } else {
                MainActivity.myAppDatabase.scenarioItemDAO().updateScenarioItem(scenarioItem);
            }
        } catch (Exception e) {
            Log.e(TAG + "ScenarioItem", e.toString());
        }
    }

    private static void insertMediaSrc(MediaSrc mediaSrc, int mediaSrcId, String url, String titleMedia, int typeId, String extension) {
        String urlLocal = "F";
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
    }

    private static void getDataFromPlaylistItemDTO(PlaylistItemDTO playlistItemDTO, int playlistId) {

        //Insert MediaSrc in sqlite
        int mediaSrcId = playlistItemDTO.getMediaSrcId();
        String titleMedia = playlistItemDTO.getTitle();
        int typeId = playlistItemDTO.getTypeId();
        String url = playlistItemDTO.getUrlMedia();
        String extension = playlistItemDTO.getExtensionMedia();
        String hashCode = playlistItemDTO.getHashCode();
        MediaSrc mediaSrc = new MediaSrc(mediaSrcId, titleMedia, typeId, url, extension, "F", hashCode, "L");
        insertMediaSrc(mediaSrc, mediaSrcId, url, titleMedia, typeId, extension);

        //Insert PlaylistItem in sqlite
        int playlistItemId = playlistItemDTO.getPlaylistItemId();
        int displayOrderPlaylistItem = playlistItemDTO.getDisplayOrder();
        long duration = playlistItemDTO.getDuration();
        PlaylistItem playlistItem = new PlaylistItem(playlistItemId, mediaSrcId, playlistId, displayOrderPlaylistItem, duration);
        insertPlaylistItem(playlistItem, playlistItemId);

    }

    private static void getDataFromScenarioItemDTO(ScenarioItemDTO scenarioItemDTO, int layoutId) {
        int playlistId = scenarioItemDTO.getPlaylistId();
        long playlistUpdate = scenarioItemDTO.getPlaylistUpdateDateTime();
        //Insert Playlist to sqlite
        Playlist playlist = new Playlist(playlistId, playlistUpdate);

        insertPlaylist(playlist, playlistId);

        int scenarioId = scenarioItemDTO.getScenarioId();
        int displayOrder = scenarioItemDTO.getDisplayOderPlaylist();
        int areaId = scenarioItemDTO.getAreaId();
        int visualTypeId = scenarioItemDTO.getVisualTypeId();

        //Insert Area to sqlite
        Area area = new Area(areaId, layoutId, visualTypeId);
        insertArea(area, areaId, layoutId);
        List<Area> areaList = MainActivity.myAppDatabase.areaDAO().getArea();
        //Insert ScenarioItem to sqlite
        ScenarioItem scenarioItem = new ScenarioItem(scenarioId, playlistId, displayOrder, layoutId, areaId);
        insertScenarioItem(scenarioItem, scenarioId, playlistId, layoutId, areaId);
    }

    private static void insertDataFromScenarioDTO(ScenarioDTO scenarioDTO) {//remove area
        int layoutId = scenarioDTO.getLayoutId();
        for (int j = 0; j < scenarioDTO.getScenarioItems().size(); j++) {
            ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(j);
            int playlistId = scenarioItemDTO.getPlaylistId();
            getDataFromScenarioItemDTO(scenarioItemDTO, layoutId);

            for (int k = 0; k < scenarioDTO.getScenarioItems().get(j).getPlaylistItems().size(); k++) {
                PlaylistItemDTO playlistItemDTO = scenarioDTO.getScenarioItems().get(j).getPlaylistItems().get(k);
                getDataFromPlaylistItemDTO(playlistItemDTO, playlistId);
            }
        }
    }

    private static void insertNewScenarioItems(ScenarioDTO scenarioDTO) {
        int layoutId = scenarioDTO.getLayoutId();
        for (int i = 0; i < scenarioDTO.getScenarioItems().size(); i++) {
            ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(i);
            int playlistId = scenarioItemDTO.getPlaylistId();
            int scenarioId = scenarioItemDTO.getScenarioId();
            int displayOrder = scenarioItemDTO.getDisplayOderPlaylist();
            int areaId = scenarioItemDTO.getAreaId();
            ScenarioItem scenarioItem = new ScenarioItem(scenarioId, playlistId, displayOrder, layoutId, areaId);
            MainActivity.myAppDatabase.scenarioItemDAO().addScenarioItem(scenarioItem);
        }
    }

    private static void insertNewAreas(ScenarioDTO scenarioDTO){
        int layoutId = scenarioDTO.getLayoutId();
        for (int i = 0; i < scenarioDTO.getScenarioItems().size(); i++) {
            ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(i);
            int areaId = scenarioItemDTO.getAreaId();
            int visualType = scenarioItemDTO.getVisualTypeId();
            Area area = new Area(areaId, layoutId, visualType);
            insertArea(area, areaId, layoutId);
        }
    }

    private static void insertNewPlaylistItems(ScenarioDTO scenarioDTO, Playlist playlist) {
        List<ScenarioItemDTO> scenarioItemDTOList = new ArrayList<>();


        for (int i = 0; i < scenarioDTO.getScenarioItems().size(); i++) {
            if (scenarioDTO.getScenarioItems().get(i).getPlaylistId() == playlist.getPlaylistId()) {
                scenarioItemDTOList.add(scenarioDTO.getScenarioItems().get(i));
            }
        }
        for (int i = 0; i < scenarioItemDTOList.size(); i++) {
            for (int j = 0; j < scenarioItemDTOList.get(i).getPlaylistItems().size(); j++) {
                PlaylistItemDTO playlistItemDTO = scenarioItemDTOList.get(i).getPlaylistItems().get(j);
                int playlistId = scenarioItemDTOList.get(i).getPlaylistId();
                int mediaSrcId = playlistItemDTO.getMediaSrcId();
                String titleMedia = playlistItemDTO.getTitle();
                int typeId = playlistItemDTO.getTypeId();
                String url = playlistItemDTO.getUrlMedia();
                String extension = playlistItemDTO.getExtensionMedia();
                String hashCode = playlistItemDTO.getHashCode();
                MediaSrc mediaSrc = new MediaSrc(mediaSrcId, titleMedia, typeId, url, extension, "F", hashCode, "L");
                insertMediaSrc(mediaSrc, mediaSrcId, url, titleMedia, typeId, extension);

                if (playlistId == playlist.getPlaylistId()) {
                    int playlistItemId = playlistItemDTO.getPlaylistItemId();
                    int displayOrderPlaylistItem = playlistItemDTO.getDisplayOrder();
                    long duration = playlistItemDTO.getDuration();
                    PlaylistItem playlistItem = new PlaylistItem(playlistItemId, mediaSrcId, playlistId, displayOrderPlaylistItem, duration);
                    insertPlaylistItem(playlistItem, playlistItemId);

                }
            }
        }

    }

    private static void updateScenario(Scenario scenario, ScenarioDTO scenarioDTO) {
        MainActivity.myAppDatabase.scenarioDAO().updateScenario(scenario); //Step 1: Update Scenario Info
        MainActivity.myAppDatabase.scenarioItemDAO().deleteScenarioItemByScenarioId(scenario.getScenarioId()); //STEP2: delete all scenarioItems
        insertNewScenarioItems(scenarioDTO);//STEP3: insert new sceario Item
    }

    private static void updatePlaylist(ScenarioDTO scenarioDTO, Playlist playlist) {
        MainActivity.myAppDatabase.playlistDAO().updatePlaylist(playlist);//Step 1: Update playlist Info
        int rowDelete;
        try {
            rowDelete = MainActivity.myAppDatabase.playlistItemDAO().deletePlaylistItemByPlaylistId(playlist.getPlaylistId());
        }catch(Exception e){
            Log.e(TAG, e.toString());
        }

        //Step 2: delete all playlist Item
        //insert playlist items to playlsit from API Data
        insertNewPlaylistItems(scenarioDTO, playlist);
    }

    private static List<PlaylistDTO> getPlaylistDTOList(ScenarioDTO scenarioDTO) {

        List<PlaylistDTO> playlistDTOList = new ArrayList<>();
        for (int i = 0; i < scenarioDTO.getScenarioItems().size(); i++) {
            ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(i);
            int playlistId = scenarioItemDTO.getPlaylistId();
            long playlistUpdateTime = scenarioItemDTO.getPlaylistUpdateDateTime();
            PlaylistDTO playlistDTO = new PlaylistDTO(playlistId, playlistUpdateTime);
            if (playlistDTOList.size() == 0) {
                playlistDTOList.add(playlistDTO);
            } else {
                boolean checkDuplicate = false;
                for (int m = 0; m < playlistDTOList.size(); m++) {

                    if (playlistDTOList.get(m).getPlaylistId() == playlistId) {
                        checkDuplicate = true;
                    }
                }
                if (!checkDuplicate) {
                    playlistDTOList.add(playlistDTO);
                }
            }

        }

        return playlistDTOList;
    }


//    public static Playlist getPlaylistOfAPIByPlaylistId(ScenarioDTO scenarioDTO){
//        for (int j = 0; j < scenarioDTO.getScenarioItems().size(); j++) {
//            ScenarioItemDTO scenarioItemDTO = scenarioDTO.getScenarioItems().get(j);
//            int playlistId = scenarioItemDTO.getPlaylistId();
//            long playlistUpdate = Supporter.calculateTime(scenarioItemDTO.getPlaylistUpdateDateTime());
//            Playlist playlist = new Playlist(playlistId, playlistUpdate);
//            return playlist;
//        }
//
//    }
}
