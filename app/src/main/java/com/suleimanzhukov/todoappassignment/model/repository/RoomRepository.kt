package com.suleimanzhukov.todoappassignment.model.repository

import androidx.room.Dao
import com.suleimanzhukov.todoappassignment.model.entities.Task
//import com.suleimanzhukov.todoappassignment.model.rest.TaskDatabase
import javax.inject.Inject

//@Dao
//class RoomRepository @Inject constructor(
//    private val database: TaskDatabase
//) : Repository {
//
//    private val getDatabase = database.dao()
//
//    override suspend fun getTasks(): MutableList<Task> {
//        return getDatabase.getAllTasks()
//    }
//
//    override suspend fun getTaskById(id: Long): Task {
//        return getDatabase.getTaskById(id)
//    }
//
//    override suspend fun uploadTask(task: Task): Task {
//        getDatabase.addTask(task)
//        return task
//    }
//
//    override suspend fun editTaskById(task: Task) {
//        getDatabase.editTask(task)
//    }
//
//    override suspend fun deleteTaskById(id: Long) {
//        getDatabase.deleteTask(id)
//    }
//
//}