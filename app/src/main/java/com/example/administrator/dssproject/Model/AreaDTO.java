package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

public class AreaDTO {

    @SerializedName("area_id")
    private int areaId;

    @SerializedName("layout_id")
    private int layoutId;

    @SerializedName("visual_type_id")
    private int visualTypeId;

    public AreaDTO(int areaId, int layoutId, int visualTypeId) {
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
