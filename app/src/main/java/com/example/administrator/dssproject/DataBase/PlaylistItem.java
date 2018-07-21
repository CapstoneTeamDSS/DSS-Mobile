package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "playlistItem",
        indices = {@Index("playlist_id"), @Index("media_id")},
//        indices = {@Index(value = {"playlist_id", "media_id"},
//        unique = true)},
        foreignKeys = {
        @ForeignKey(
                entity = MediaSrc.class,
                parentColumns = "media_id",
                childColumns = "media_id"
        ),
        @ForeignKey(
                entity = Playlist.class,
                parentColumns = "playlist_id",
                childColumns = "playlist_id"
        )
})
public class PlaylistItem implements Comparable<PlaylistItem>{

    @PrimaryKey
    @ColumnInfo(name = "playlistItem_id")
    private int playlistItemId;

    @ColumnInfo(name = "media_id")
    private int mediaSrcId;

    @ColumnInfo(name = "playlist_id")
    private int playlistId;

    @ColumnInfo(name = "playlistItem_displayOrder")
    private int displayOrder;

    @ColumnInfo(name = "playlistItem_duration")
    private String duration;

    public PlaylistItem(int playlistItemId, int mediaSrcId, int playlistId, int displayOrder, String duration) {
        this.playlistItemId = playlistItemId;
        this.mediaSrcId = mediaSrcId;
        this.playlistId = playlistId;
        this.displayOrder = displayOrder;
        this.duration = duration;
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

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(@NonNull PlaylistItem playlistItem) {
        int thisDisplayorder = this.getDisplayOrder();
        int compareDisplayorder = playlistItem.getDisplayOrder();
        return thisDisplayorder - compareDisplayorder;
    }
}
