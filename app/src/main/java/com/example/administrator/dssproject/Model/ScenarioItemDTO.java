package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScenarioItemDTO {

    @SerializedName("scenario_id")
    private int scenarioId;

    @SerializedName("playlist_id")
    private int playlistId;

    @SerializedName("playlist_update_datetime")
    private long playlistUpdateDateTime;

    @SerializedName("display_order_playlist")
    private int displayOderPlaylist;

    @SerializedName("area_id")
    private int areaId;

    @SerializedName("visual_type_id")
    private int visualTypeId;

    @SerializedName("playlist_items")
    private List<PlaylistItemDTO> playlistItems;

    public ScenarioItemDTO(int scenarioId, int playlistId, long playlistUpdateDateTime, int displayOderPlaylist, int areaId, int visualTypeId, List<PlaylistItemDTO> playlistItems) {
        this.scenarioId = scenarioId;
        this.playlistId = playlistId;
        this.playlistUpdateDateTime = playlistUpdateDateTime;
        this.displayOderPlaylist = displayOderPlaylist;
        this.areaId = areaId;
        this.visualTypeId = visualTypeId;
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

    public int getVisualTypeId() {
        return visualTypeId;
    }

    public void setVisualTypeId(int visualTypeId) {
        this.visualTypeId = visualTypeId;
    }

    public long getPlaylistUpdateDateTime() {
        return playlistUpdateDateTime;
    }

    public void setPlaylistUpdate(long playlistUpdateDateTime) {
        this.playlistUpdateDateTime = playlistUpdateDateTime;
    }
}
