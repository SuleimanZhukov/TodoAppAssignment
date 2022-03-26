package com.suleimanzhukov.todoappassignment.model.repository

import com.suleimanzhukov.todoappassignment.model.entities.Task
import com.suleimanzhukov.todoappassignment.model.entities.TaskEntity
import com.suleimanzhukov.todoappassignment.model.rest.ListApiService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : Repository {

    private val listApi = retrofit.create<ListApiService>()

    override suspend fun getTasks(): MutableList<Task> {
        return convertListEntitiesToTasks(listApi.getAllTasks().execute().body()!!)
    }

    override suspend fun getTaskById(id: Long): Task {
        return convertEntityToTask(listApi.getTasksById(id).execute().body()!!)
    }

    override suspend fun uploadTask(task: Task): Task {
        listApi.uploadTask(convertTaskToEntity(task))
        return task
    }

    override suspend fun editTaskById(task: Task) {
        listApi.editTaskById(task.id)
    }

    override suspend fun deleteTaskById(id: Long) {
        listApi.deleteTaskById(id)
    }

    private fun convertTaskToEntity(task: Task) = TaskEntity(
        task.id, task.name, task.priority
    )

    private fun convertListEntitiesToTasks(taskEntities: MutableList<TaskEntity>): MutableList<Task> {
        return taskEntities.map {
            Task(
                it.id, it.name, it.priority
            )
        }.toMutableList()
    }

    private fun convertEntityToTask(taskEntity: TaskEntity) = Task(
        taskEntity.id, taskEntity.name, taskEntity.priority
    )
}