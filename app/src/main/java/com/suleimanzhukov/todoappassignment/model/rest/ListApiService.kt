package com.suleimanzhukov.todoappassignment.model.rest

import com.suleimanzhukov.todoappassignment.model.entities.TaskEntity
import retrofit2.Call
import retrofit2.http.*

interface ListApiService {

    @GET("lists")
    fun getAllTasks(): Call<MutableList<TaskEntity>>

    @GET("lists/{id}")
    fun getTasksById(
        @Path("id") id: Long
    ): Call<TaskEntity>

    @POST("lists")
    fun uploadTask(
        @Body task: TaskEntity
    ): Call<TaskEntity>

    @PUT("lists/{id}")
    fun editTaskById(
        @Path("id") id: Long
    ): Call<TaskEntity>

    @DELETE("lists/{id}")
    fun deleteTaskById(
        @Path ("id") id: Long
    ): Call<TaskEntity>
}