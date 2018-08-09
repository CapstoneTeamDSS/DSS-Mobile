package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LayoutDAO {

    @Insert
    public void addLayout(Layout layout);

    @Query("SELECT * FROM layout")
    public List<Layout> getLayout();

    @Query("SELECT * FROM layout WHERE layout_id = :id")
    public Layout getALayout(int id);

    @Update
    public void updateLayout(Layout layout);
}
