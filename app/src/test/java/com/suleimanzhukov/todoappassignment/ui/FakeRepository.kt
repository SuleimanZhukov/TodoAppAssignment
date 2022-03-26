package com.suleimanzhukov.todoappassignment.ui

import com.suleimanzhukov.todoappassignment.model.entities.Task
import com.suleimanzhukov.todoappassignment.model.repository.Repository

class FakeRepository : Repository {

    private val fakeList = mutableListOf<Task>()

    override suspend fun getTasks(): MutableList<Task> {
        return fakeList
    }

    override suspend fun getTaskById(id: Long): Task {
        return fakeList.find { it.id == id }!!
    }

    override suspend fun uploadTask(task: Task): Task {
        fakeList.add(task)
        return fakeList.find { it.id == task.id }!!
    }

    override suspend fun editTaskById(task: Task) {
        //
    }

    override suspend fun deleteTaskById(id: Long) {
        //
    }
}