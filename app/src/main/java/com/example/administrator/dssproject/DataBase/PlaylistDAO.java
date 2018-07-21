package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlaylistDAO {

    @Insert
    public void addPlaylist(Playlist playlist);

    @Query("SELECT * FROM playlist")
    public List<Playlist> getPlaylists();

    @Query("SELECT * FROM playlist WHERE playlist_id = :id")
    public Playlist getAPlaylist(int id);

    @Update
    public void updatePlaylist(Playlist playlist);

    @Delete
    public void deletePlaylist(Playlist playlist);

    @Query("DELETE FROM playlist")
    public void deleteAll();

    @Query("SELECT * FROM playlist" +
            " WHERE playlist_id IN (SELECT playlist_id FROM scenarioItem"  +
            " WHERE scenario_id IN (SELECT scenario_id FROM scenario" +
            " WHERE scenario_id = :id))")
    public List<Playlist> getPlaylistListByScenarioItem(int id);

    @Query("SELECT * FROM playlist" +
            " WHERE playlist_id IN (SELECT playlist_id FROM scenarioItem" +
            " WHERE playlist_id = :id)")
    public List<Playlist> getPlaylistListByPlaylistId(int id);


}
