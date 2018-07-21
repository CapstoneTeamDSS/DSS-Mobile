package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "playlist")
public class Playlist {

    @PrimaryKey
    @ColumnInfo(name = "playlist_id")
    private int playlistId;

    public Playlist(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}
