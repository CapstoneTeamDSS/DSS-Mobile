package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlaylistItemDAO {

    @Insert
    public void addPlaylistItem(PlaylistItem playlistItem);

    @Query("SELECT * FROM playlistItem")
    public List<PlaylistItem> getPlaylistItems();

    @Query("SELECT * FROM playlistItem WHERE playlistItem_id = :id")
    public PlaylistItem getAPlaylistItem(int id);

    @Update
    public void updatePlaylistItem(PlaylistItem playlistItem);

    @Delete
    public void deletePlaylistItem(PlaylistItem playlistItem);

    @Query("DELETE FROM playlistItem")
    public void deleteAll();

    @Query("SELECT * FROM playlistItem" +
            " WHERE playlist_id IN (SELECT playlist_id FROM playlist" +
            " WHERE playlist_id = :id)" +
            " ORDER BY playlistItem_displayOrder ASC")
    public List<PlaylistItem> getPlaylistItemByPlaylistId(int id);
    
}
