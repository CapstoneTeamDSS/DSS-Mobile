package com.example.administrator.dssproject.Utils;

import java.util.Calendar;

public class TimeSlot {
    private Calendar startTime;
    private Calendar endTime;

    public TimeSlot(Calendar startTime, Calendar endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
