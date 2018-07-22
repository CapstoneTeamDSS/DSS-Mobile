package com.example.administrator.dssproject.API;

import com.example.administrator.dssproject.Model.ScheduleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://192.168.1.101:80/api/";

    @GET("Schedule")
    Call<List<ScheduleDTO>> getScheduleByBoxId(@Query("boxId") Integer boxId);
}
