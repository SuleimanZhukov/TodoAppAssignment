package com.suleimanzhukov.todoappassignment.ui

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.suleimanzhukov.todoappassignment.TodoApp
import com.suleimanzhukov.todoappassignment.model.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ScreenTest @Inject constructor(
    private val repository: Repository
) {


    @Before
    fun setUp() {

    }

    @Test
    fun clickAddFloatingButtonTest() {


    }
}