package com.example.administrator.dssproject.Utils;

import java.util.Calendar;

public class EnumTimeSlot {

    enum TimeSlot{
        TIME_SLOT1();

    }

    public Calendar getStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal;
    }

    public Calendar getEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 1);
        cal.set(Calendar.MINUTE, 30);
        return cal;
    }
}
