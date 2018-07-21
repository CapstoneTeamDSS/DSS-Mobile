package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScheduleDAO {

    @Insert
    public void addSchedule(Schedule schedule);

    @Query("SELECT * FROM schedule")
    public List<Schedule> getSchedules();

    @Query("SELECT * FROM schedule WHERE schedule_id = :id")
    public Schedule getASchedule(int id);

    @Update
    public void updateSchedule(Schedule schedule);

    @Delete
    public void deleteSchedule(Schedule schedule);

    @Query("DELETE FROM schedule")
    public void deleteAll();


}

