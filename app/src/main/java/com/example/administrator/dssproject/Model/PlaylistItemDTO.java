package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

public class PlaylistItemDTO {

    @SerializedName("playlist_item_id")
    public int playlistItemId;

    @SerializedName("mediasrc_id")
    public int mediaSrcId;

    @SerializedName("display_order_media")
    public int displayOrder;

    @SerializedName("duration")
    public long duration;

    @SerializedName("url_media")
    public String urlMedia;

    @SerializedName("title_media")
    public String title;

    @SerializedName("extension_media")
    public String extensionMedia;

    @SerializedName("type_id")
    public int typeId;

    public PlaylistItemDTO() {
    }

    public PlaylistItemDTO(int playlistItemId, int mediaSrcId, int displayOrder, long duration, String urlMedia, String title, String extensionMedia, int typeId) {
        this.playlistItemId = playlistItemId;
        this.mediaSrcId = mediaSrcId;
        this.displayOrder = displayOrder;
        this.duration = duration;
        this.urlMedia = urlMedia;
        this.title = title;
        this.extensionMedia = extensionMedia;
        this.typeId = typeId;
    }

    public int getPlaylistItemId() {
        return playlistItemId;
    }

    public void setPlaylistItemId(int playlistItemId) {
        this.playlistItemId = playlistItemId;
    }

    public int getMediaSrcId() {
        return mediaSrcId;
    }

    public void setMediaSrcId(int mediaSrcId) {
        this.mediaSrcId = mediaSrcId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtensionMedia() {
        return extensionMedia;
    }

    public void setExtensionMedia(String extensionMedia) {
        this.extensionMedia = extensionMedia;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
