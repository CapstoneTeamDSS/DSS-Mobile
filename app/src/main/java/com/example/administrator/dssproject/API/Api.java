package com.example.administrator.dssproject.API;

import com.example.administrator.dssproject.Model.ScenarioDTO;
import com.example.administrator.dssproject.Model.ScheduleDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://192.168.1.4:80/api/";

    @GET("Schedule")
    Call<ScheduleDTO> getScenarioByBoxId(@Query("boxId") Integer boxId);
}
