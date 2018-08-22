package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScenarioDAO {

    @Insert
    public void addScenario(Scenario scenario);

    @Query("SELECT * FROM scenario")
    public List<Scenario> getScenarios();

    @Query("SELECT * FROM scenario WHERE scenario_id = :id")
    public Scenario getAScenario(int id);

    @Update
    public void updateScenario(Scenario scenario);

    @Delete
    public void deleteScenario(Scenario scenario);

    @Query("DELETE FROM scenario")
    public void deleteAll();

    @Query("SELECT audio_area FROM scenario WHERE scenario_id = :id ")
    public int getAudioArea(int id);

}
