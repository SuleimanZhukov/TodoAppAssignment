package com.suleimanzhukov.todoappassignment.ui

import com.suleimanzhukov.todoappassignment.model.entities.Task
import com.suleimanzhukov.todoappassignment.model.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DialogFragmentAddTest {

    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        viewModel = ListViewModel(FakeRepository())

        val taskToGet = mutableListOf<Task>()
        for (i in 1..10) {
            taskToGet.add(Task(i.toLong(), "name", i, false))
        }

        runBlocking {
            taskToGet.forEach {
                viewModel.addTask(it)
            }
        }
    }

    @Test
    fun getAllTasksTest() {
        var tasks = mutableListOf<Task>()
        runBlocking {
            tasks = viewModel.getAllTasksTest()
        }

        assertTrue(tasks.size == 10)
    }
}