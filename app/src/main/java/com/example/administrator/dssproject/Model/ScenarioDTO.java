package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScenarioDTO {

    @SerializedName("scenario_id")
    private int scenarioId;

    @SerializedName("layout_id")
    private int layoutId;

    @SerializedName("schedule_title")
    private String title;


    @SerializedName("scenario_items")
    private List<ScenarioItemDTO> scenarioItems;

    public ScenarioDTO() {
    }

    public ScenarioDTO(int scenarioId, int layoutId, String title, List<ScenarioItemDTO> scenarioItems) {
        this.scenarioId = scenarioId;
        this.layoutId = layoutId;
        this.title = title;
        this.scenarioItems = scenarioItems;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScenarioItemDTO> getScenarioItems() {
        return scenarioItems;
    }

    public void setScenarioItems(List<ScenarioItemDTO> scenarioItems) {
        this.scenarioItems = scenarioItems;
    }
}
