package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "mediaSrc",
        indices = {@Index(value = {"media_id"},
                unique = true)})
public class MediaSrc {

    @PrimaryKey
    @ColumnInfo(name = "media_id")
    private int mediaSrcID;

    @ColumnInfo(name = "media_title")
    private String title;

    @ColumnInfo(name = "media_typeID")
    private int typeID;

    @ColumnInfo(name = "media_url")
    private String url;

    @ColumnInfo(name = "media_extension")
    private String extension;


    @ColumnInfo(name = "media_urlLocal")
    private String urlLocal;


    @ColumnInfo(name = "media_last_use")
    private String lastUsed;

    public MediaSrc(int mediaSrcID, String title, int typeID, String url, String extension, String urlLocal, String lastUsed) {
        this.mediaSrcID = mediaSrcID;
        this.title = title;
        this.typeID = typeID;
        this.url = url;
        this.extension = extension;
        this.urlLocal = urlLocal;
        this.lastUsed = lastUsed;
    }

    public MediaSrc() {

    }


    public int getMediaSrcID() {
        return mediaSrcID;
    }

    public void setMediaSrcID(int mediaSrcID) {
        this.mediaSrcID = mediaSrcID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUrlLocal() {
        return urlLocal;
    }

    public void setUrlLocal(String urlLocal) {
        this.urlLocal = urlLocal;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }
}
