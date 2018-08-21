package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "scenario",
        indices = {@Index(value = "scenario_id", unique = true), @Index("layout_id")},
        primaryKeys = {"scenario_id", "layout_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Layout.class,
                        parentColumns = "layout_id",
                        childColumns = "layout_id")
})
public class Scenario {

    @ColumnInfo(name = "scenario_id")
    private int scenarioId;

    @ColumnInfo(name = "scenario_title")
    private String title;

    @ColumnInfo(name = "scenario_update_datetime")
    private long scenarioUpdateDateTime;

    @ColumnInfo(name = "layout_id")
    private int layoutId;

    @ColumnInfo(name = "audio_area")
    private int audioArea;

    @ColumnInfo(name = "add_time")
    private long addTime;


    public Scenario(int scenarioId, String title, long scenarioUpdateDateTime, int layoutId, int audioArea,  long addTime) {
        this.scenarioId = scenarioId;
        this.title = title;
        this.scenarioUpdateDateTime = scenarioUpdateDateTime;
        this.layoutId = layoutId;
        this.audioArea = audioArea;
        this.addTime = addTime;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public int getAudioArea() {
        return audioArea;
    }

    public void setAudioArea(int audioArea) {
        this.audioArea = audioArea;
    }

    public long getScenarioUpdateDateTime() {
        return scenarioUpdateDateTime;
    }

    public void setScenarioUpdateDateTime(long scenarioUpdateDateTime) {
        this.scenarioUpdateDateTime = scenarioUpdateDateTime;
    }
}
