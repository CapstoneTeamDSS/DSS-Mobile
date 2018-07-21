package com.example.administrator.dssproject.Model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.List;

public class ScheduleDTO {

    @SerializedName("schedule_id")
    public int scheduleId;

    @SerializedName("scenario_id")
    public int scenarioId;

    @SerializedName("layout_id")
    public int layoutId;

    @SerializedName("schedule_title")
    public String title;

    @SerializedName("times_to_play")
    public int timesToPlay;

    @SerializedName("start_time")
    public String startTime;

    @SerializedName("end_time")
    public String endTime;

    @SerializedName("scenario_items")
    public List<ScenarioItemDTO> scenarioItems;

    public ScheduleDTO() {
    }

    public ScheduleDTO(int scheduleId, int scenarioId, int layoutId, String title, int timesToPlay, String startTime, String endTime, List<ScenarioItemDTO> scenarioItems) {
        this.scheduleId = scheduleId;
        this.scenarioId = scenarioId;
        this.layoutId = layoutId;
        this.title = title;
        this.timesToPlay = timesToPlay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scenarioItems = scenarioItems;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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

    public int getTimesToPlay() {
        return timesToPlay;
    }

    public void setTimesToPlay(int timesToPlay) {
        this.timesToPlay = timesToPlay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<ScenarioItemDTO> getScenarioItems() {
        return scenarioItems;
    }

    public void setScenarioItems(List<ScenarioItemDTO> scenarioItems) {
        this.scenarioItems = scenarioItems;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }
}
