package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScenarioItemDTO {

    @SerializedName("scenario_id")
    private int scenarioId;

    @SerializedName("playlist_id")
    private int playlistId;

    @SerializedName("display_order_playlist")
    public int displayOderPlaylist;

    @SerializedName("area_id")
    public int areaId;

    @SerializedName("playlist_items")
    public List<PlaylistItemDTO> playlistItems;

    public ScenarioItemDTO() {
    }

    public ScenarioItemDTO( int scenarioId, int playlistId, int displayOderPlaylist, int areaId, List<PlaylistItemDTO> playlistItems) {

        this.scenarioId = scenarioId;
        this.playlistId = playlistId;
        this.displayOderPlaylist = displayOderPlaylist;
        this.areaId = areaId;
        this.playlistItems = playlistItems;
    }



    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getDisplayOderPlaylist() {
        return displayOderPlaylist;
    }

    public void setDisplayOderPlaylist(int displayOderPlaylist) {
        this.displayOderPlaylist = displayOderPlaylist;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public List<PlaylistItemDTO> getPlaylistItems() {
        return playlistItems;
    }

    public void setPlaylistItems(List<PlaylistItemDTO> playlistItems) {
        this.playlistItems = playlistItems;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }
}
