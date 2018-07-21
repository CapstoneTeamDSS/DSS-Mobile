package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "box")
public class Box {

    @PrimaryKey
    @ColumnInfo(name = "box_id")
    public int boxId;

    public Box(int boxId) {
        this.boxId = boxId;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }
}
