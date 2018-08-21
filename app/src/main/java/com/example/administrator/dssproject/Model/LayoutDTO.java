package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

public class LayoutDTO {

    @SerializedName("layout_id")
    private int layoutId;

    public LayoutDTO(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
}
