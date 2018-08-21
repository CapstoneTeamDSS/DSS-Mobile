package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

public class PlaylistDTO {

    @SerializedName("playlist_id")
    private int playlistId;

    @SerializedName("playlist_update_datetime")
    private long playlistUpdateDateTime;

    public PlaylistDTO(int playlistId, long playlistUpdateDateTime) {
        this.playlistId = playlistId;
        this.playlistUpdateDateTime = playlistUpdateDateTime;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public long getPlaylistUpdateDateTime() {
        return playlistUpdateDateTime;
    }

    public void setPlaylistUpdateDateTime(long playlistUpdateDateTime) {
        this.playlistUpdateDateTime = playlistUpdateDateTime;
    }
}
