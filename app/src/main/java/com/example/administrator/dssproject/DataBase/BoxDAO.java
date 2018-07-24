package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BoxDAO {

    @Insert
    public void addBox(Box box);

    @Query("SELECT * FROM box")
    public List<Box> getBox();

    @Query("SELECT box_id FROM box WHERE box_id = ''")
    public int getBoxId();

    @Update
    public void updateBox(Box box);

    @Delete
    public void deleteBox(Box box);
}
