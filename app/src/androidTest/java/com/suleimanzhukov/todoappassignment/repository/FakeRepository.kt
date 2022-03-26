package com.suleimanzhukov.todoappassignment.repository

import com.suleimanzhukov.todoappassignment.model.entities.Task
import com.suleimanzhukov.todoappassignment.model.repository.Repository

class FakeRepository : Repository {

    private var fakeTasksList = mutableListOf<Task>()

    override suspend fun getTasks(): MutableList<Task> {
        return fakeTasksList
    }

    override suspend fun getTaskById(id: Long): Task {
        return fakeTasksList.find { it.id == id }!!
    }

    override suspend fun uploadTask(task: Task): Task {
        fakeTasksList.add(task)
        return task
    }

    override suspend fun editTaskById(task: Task) {
        //
    }

    override suspend fun deleteTaskById(id: Long) {
        //
    }
}