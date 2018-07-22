package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.Log;

import java.util.List;

@Dao
public interface MediaSrcDAO {

    @Insert
    public void addMediaSrc(MediaSrc mediaSrc);

    @Query("SELECT * FROM mediaSrc")
    public List<MediaSrc> getMediaSrc();

    @Query("SELECT * FROM mediaSrc WHERE media_id = :id")
    public MediaSrc getAMediaSrc(int id);

    @Update
    public void updateMediaSrc(MediaSrc mediaSrc);

    @Query("UPDATE mediaSrc SET media_title = :title, media_typeID = :typeID, media_url = :url, media_extension = :extension," +
            " media_urlLocal =:urlLocal, media_last_use =:lastUsed  WHERE media_id = :mediaSrcID")
    public void updateMediaSrcExceptLocalUrl(int mediaSrcID, String title, int typeID, String url,
                                             String extension, String urlLocal, String lastUsed);

    @Delete
    public void deleteMediaSrc(MediaSrc mediaSrc);

    @Query("DELETE FROM mediaSrc")
    public void deleteAll();

    @Query("SELECT * FROM mediaSrc " +
            " WHERE media_id IN (SELECT media_id FROM playlistItem" +
            " WHERE playlist_id IN (SELECT playlist_id FROM playlist " +
            " WHERE playlist_id IN (SELECT playlist_id FROM scenarioItem " +
            " WHERE scenario_id IN (SELECT scenario_id FROM scenario" +
            " WHERE scenario_id IN (SELECT scenario_id FROM schedule " +
            " WHERE schedule_id = :id)))))")
    public List<MediaSrc> getMediaByScheduleId(int id);


    @Query("SELECT * FROM mediaSrc " +
            " WHERE media_id IN (SELECT media_id FROM playlistItem " +
            " WHERE playlist_id IN (SELECT playlist_id FROM playlist " +
            " WHERE playlist_id = :id))")
    public List<MediaSrc> getMediaByPlaylistId(int id);

    @Query("SELECT * FROM mediaSrc " +
            " WHERE media_id IN (SELECT media_id FROM playlistItem " +
            " WHERE playlistItem_id = :id)")
    public MediaSrc getAMediaSrcByPlaylistItemId(int id);


    @Query("SELECT playlistItem_duration FROM playlistItem" +
            " WHERE media_id = :id AND playlistItem_id = :playlistItemId")
    public long getDurationByMediaSrcId(int id, int playlistItemId);
}
