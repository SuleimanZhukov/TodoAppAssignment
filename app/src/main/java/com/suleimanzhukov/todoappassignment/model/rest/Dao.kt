package com.suleimanzhukov.todoappassignment.model.rest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.suleimanzhukov.todoappassignment.model.entities.Task

//@Dao
//interface Dao {
//
//    @Insert(onConflict = IGNORE)
//    suspend fun addTask(task: Task)
//
//    @Query("SELECT * FROM Task")
//    suspend fun getAllTasks(): MutableList<Task>
//
//    @Query("SELECT * FROM Task WHERE id = :id")
//    suspend fun getTaskById(id: Long): Task
//
//    @Update
//    suspend fun editTask(task: Task)
//
//    @Query("DELETE FROM Task WHERE id = :id")
//    suspend fun deleteTask(id: Long)
//}