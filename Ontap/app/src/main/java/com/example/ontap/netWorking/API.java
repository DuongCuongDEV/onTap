package com.example.ontap.netWorking;
import com.example.ontap.model.task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    @GET("student/Student")
    Call<List<task>> getTask();

    @POST("student/Student")
    Call<task> addTask(@Body task newTask);

    @DELETE("student/Student/{id}")
    Call<task> deleteTask(@Path("id") String taskId);

    @PUT("student/Student/{id}")
    Call<task> updateTask(@Path("id") String taskId, @Body task updatedTask);

}



