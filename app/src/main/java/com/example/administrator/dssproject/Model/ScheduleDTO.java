package com.example.administrator.dssproject.Model;

import com.example.administrator.dssproject.DataBase.Scenario;
import com.google.gson.annotations.SerializedName;

public class ScheduleDTO {


    @SerializedName("scenario")
    private ScenarioDTO scenario;

    @SerializedName("start_time")
    private long startTime;

    @SerializedName("end_time")
    private long endTime;

    public ScheduleDTO(ScenarioDTO scenario, long startTime, long endTime) {
        this.scenario = scenario;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ScenarioDTO getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioDTO scenario) {
        this.scenario = scenario;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
