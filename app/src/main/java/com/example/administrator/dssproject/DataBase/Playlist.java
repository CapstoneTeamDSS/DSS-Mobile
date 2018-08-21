package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "playlist")
public class Playlist {

    @PrimaryKey
    @ColumnInfo(name = "playlist_id")
    private int playlistId;

    @ColumnInfo(name = "playlist_update_datetime")
    private long playlistUpdate;

    public Playlist(int playlistId, long playlistUpdate) {
        this.playlistId = playlistId;
        this.playlistUpdate = playlistUpdate;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public long getPlaylistUpdate() {
        return playlistUpdate;
    }

    public void setPlaylistUpdate(long playlistUpdate) {
        this.playlistUpdate = playlistUpdate;
    }
}
