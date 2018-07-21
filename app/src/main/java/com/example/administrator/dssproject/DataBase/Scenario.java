package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "scenario")
public class Scenario {

    @PrimaryKey
    @ColumnInfo(name = "scenario_id")
    private int scenarioId;

    public Scenario(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }
}
