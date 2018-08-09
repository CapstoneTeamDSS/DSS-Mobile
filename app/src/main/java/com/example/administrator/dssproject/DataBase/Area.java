package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "area",
        indices = {@Index("layout_id")},
        primaryKeys = {"area_id", "layout_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Layout.class,
                        parentColumns = "layout_id",
                        childColumns = "layout_id")
        })
public class Area {

    @ColumnInfo(name = "area_id")
    private int areaId;

    @ColumnInfo(name = "layout_id")
    private int layoutId;

    @ColumnInfo(name = "visual_type_id")
    private int visualTypeId;

    public Area(int areaId, int layoutId, int visualTypeId) {
        this.areaId = areaId;
        this.layoutId = layoutId;
        this.visualTypeId = visualTypeId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getVisualTypeId() {
        return visualTypeId;
    }

    public void setVisualTypeId(int visualTypeId) {
        this.visualTypeId = visualTypeId;
    }
}
