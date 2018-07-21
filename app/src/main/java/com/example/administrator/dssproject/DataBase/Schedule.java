package com.example.administrator.dssproject.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

@Entity(tableName = "schedule",
        indices = {@Index("scenario_id")},
        foreignKeys = {
        @ForeignKey(
                entity = Scenario.class,
                parentColumns = "scenario_id",
                childColumns = "scenario_id"
        )}
)
public class Schedule implements Comparable<Schedule> {

    @PrimaryKey
    @ColumnInfo(name = "schedule_id")
    private int scheduleId;

    @ColumnInfo(name = "scenario_id")
    private int scenarioId;

    @ColumnInfo(name = "schedule_title")
    private String title;

    @ColumnInfo(name = "schedule_layout_id")
    private int layoutId;

    @ColumnInfo(name = "schedule_start_time")
    private String startTime;

    @ColumnInfo(name = "schedule_end_time")
    private String endTime;

    @ColumnInfo(name = "schedule_times_to_play")
    private int timesToPlay;

    public Schedule(int scheduleId, int scenarioId, String title, int layoutId, String startTime, String endTime, int timesToPlay) {
        this.scheduleId = scheduleId;
        this.scenarioId = scenarioId;
        this.title = title;
        this.layoutId = layoutId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timesToPlay = timesToPlay;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
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

    public int getTimesToPlay() {
        return timesToPlay;
    }

    public void setTimesToPlay(int timesToPlay) {
        this.timesToPlay = timesToPlay;
    }

    @Override
    public int compareTo(@NonNull Schedule schedule) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try{
            Date thisStartTime = simpleDateFormat.parse(this.getStartTime());
            Date comStartTime = simpleDateFormat.parse(schedule.getStartTime());
            return thisStartTime.compareTo(comStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
