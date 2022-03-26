package com.suleimanzhukov.todoappassignment.model.repository

import com.suleimanzhukov.todoappassignment.model.entities.Task

interface Repository {

    suspend fun getTasks(): MutableList<Task>

    suspend fun getTaskById(id: Long): Task

    suspend fun uploadTask(task: Task): Task

    suspend fun editTaskById(task: Task)

    suspend fun deleteTaskById(id: Long)
}