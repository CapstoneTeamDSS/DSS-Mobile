package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AreaDAO {

    @Insert
    public void addArea(Area area);

    @Query("SELECT * FROM area")
    public List<Area> getArea();

    @Query("SELECT * FROM area WHERE area_id = :id")
    public Area getAArea(int id);

    @Update
    public void updateArea(Area area);

    @Query("SELECT area_id FROM area WHERE layout_id = :id ORDER BY area_id")
    public int[] areaIds(int id);

    @Query("SELECT visual_type_id FROM area WHERE area_id = :id")
    public int getVisualTypeId(int id);

    @Query("DELETE FROM area WHERE layout_id = :id")
    public int deleteAreaByLayoutId(int id);
}
