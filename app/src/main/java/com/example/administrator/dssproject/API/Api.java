package com.example.administrator.dssproject.API;

import com.example.administrator.dssproject.Model.ScenarioDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://192.168.50.244:80/api/";

    @GET("Schedule")
    Call<ScenarioDTO> getScenarioByBoxId(@Query("boxId") Integer boxId);
}
