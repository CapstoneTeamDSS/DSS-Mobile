package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "scenario")
public class Scenario {

    @PrimaryKey
    @ColumnInfo(name = "scenario_id")
    private int scenarioId;

    @ColumnInfo(name = "scenario_title")
    private String title;

    @ColumnInfo(name = "scenario_layout_id")
    private int layoutId;


    public Scenario(int scenarioId, String title, int layoutId) {
        this.scenarioId = scenarioId;
        this.title = title;
        this.layoutId = layoutId;

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

}
