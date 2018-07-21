package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "scenarioItem",
        indices = {@Index("scenario_id"), @Index("playlist_id")},
        primaryKeys = {"scenario_id", "playlist_id", "area_id"},
        foreignKeys = {
        @ForeignKey(
                entity =  Scenario.class,
                parentColumns = "scenario_id",
                childColumns = "scenario_id"
        ),
        @ForeignKey(
                entity = Playlist.class,
                parentColumns = "playlist_id",
                childColumns = "playlist_id"
        )
        })

public class ScenarioItem implements Comparable<ScenarioItem>{

//    @PrimaryKey
//    @ColumnInfo(name = "scenario_item_id")
//    private int scenarioItemId;

    @ColumnInfo(name = "scenario_id")
    private int scenarioId;

    @ColumnInfo(name = "playlist_id")
    private int playlistId;

    @ColumnInfo(name = "scenario_display_order")
    private int displayOrder;

    @ColumnInfo(name = "area_id")
    private int areaId;


    public ScenarioItem(int scenarioId, int playlistId, int displayOrder, int areaId) {
        this.scenarioId = scenarioId;
        this.playlistId = playlistId;
        this.displayOrder = displayOrder;
        this.areaId = areaId;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }


    @Override
    public int compareTo(@NonNull ScenarioItem scenarioItem) {

        int thisDisplayorder = this.getDisplayOrder();
        int compareDisplayorder = scenarioItem.getDisplayOrder();
        return thisDisplayorder - compareDisplayorder;

    }
}
