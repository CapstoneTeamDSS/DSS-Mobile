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

}
