package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScenarioItemDAO {

    @Insert
    public void addScenarioItem(ScenarioItem scenarioItem);

    @Query("SELECT * FROM scenarioItem")
    public List<ScenarioItem> getScenarioItems();

    @Query("SELECT * FROM scenarioItem WHERE scenario_id = :scenarioId AND playlist_id = :layoutId AND area_id = :areaId")
    public ScenarioItem getAScenarioItem(int scenarioId, int layoutId, int areaId);

    @Update
    public void updateScenarioItem(ScenarioItem scenarioItem);

    @Delete
    public void deleteScenarioItem(ScenarioItem scenarioItem);

    @Query("DELETE FROM scenarioItem")
    public void deleteAll();

    @Query("SELECT * FROM scenarioItem" +
            " WHERE scenario_id IN (SELECT scenario_id FROM scenario" +
            " WHERE scenario_id = :id)")
    public List<ScenarioItem> getScenarioItemLIistByScenarioId(int id);

    @Query("SELECT * FROM scenarioItem WHERE area_id = :id ORDER BY scenario_display_order ASC")
    public List<ScenarioItem> getScenarioItemListByAreaId(int id);
}
