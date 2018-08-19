package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

public class PlaylistItemDTO {

    @SerializedName("playlist_item_id")
    private int playlistItemId;

    @SerializedName("mediasrc_id")
    private int mediaSrcId;

    @SerializedName("display_order_media")
    private int displayOrder;

    @SerializedName("duration")
    private long duration;

    @SerializedName("url_media")
    private String urlMedia;

    @SerializedName("security_hash")
    private String hashCode;

    @SerializedName("title_media")
    private String title;

    @SerializedName("extension_media")
    private String extensionMedia;

    @SerializedName("type_id")
    private int typeId;

    public PlaylistItemDTO() {
    }

    public PlaylistItemDTO(int playlistItemId, int mediaSrcId, int displayOrder, long duration, String urlMedia, String hashCode, String title, String extensionMedia, int typeId) {
        this.playlistItemId = playlistItemId;
        this.mediaSrcId = mediaSrcId;
        this.displayOrder = displayOrder;
        this.duration = duration;
        this.urlMedia = urlMedia;
        this.hashCode = hashCode;
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

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
