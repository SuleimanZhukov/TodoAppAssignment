package com.suleimanzhukov.todoappassignment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.todoappassignment.model.entities.Task
import com.suleimanzhukov.todoappassignment.model.repository.Repository
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val tasksList: MutableLiveData<MutableList<Task>> = MutableLiveData()

    fun getTasksListLiveData() = tasksList

    suspend fun addTask(task: Task) {
        repository.uploadTask(task)
    }

    suspend fun getAllTasks() {
        tasksList.postValue(repository.getTasks())
    }

    suspend fun getAllTasksTest(): MutableList<Task> {
        return repository.getTasks()
    }

    suspend fun getTaskById(id: Long): Task {
        return repository.getTaskById(id)
    }

    suspend fun editTaskById(task: Task) {
        repository.editTaskById(task)
    }

    suspend fun deleteTaskById(task: Task) {
        repository.deleteTaskById(task.id)
    }
}