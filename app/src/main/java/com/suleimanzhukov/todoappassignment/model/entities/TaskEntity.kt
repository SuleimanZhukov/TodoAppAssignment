package com.suleimanzhukov.todoappassignment.model.entities

data class TaskEntity(
    val id: Long,
    var name: String,
    var priority: Int
)