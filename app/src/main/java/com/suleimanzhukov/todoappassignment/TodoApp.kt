package com.suleimanzhukov.todoappassignment

import android.app.Application
import com.suleimanzhukov.todoappassignment.di.AppComponent
import com.suleimanzhukov.todoappassignment.di.ContextModule
import com.suleimanzhukov.todoappassignment.di.DaggerAppComponent
import com.suleimanzhukov.todoappassignment.di.NetworkModule

open class TodoApp : Application() {

    val appComponent = lazy {
        initComponent()
    }

    open fun initComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .networkModule(NetworkModule("https://62383d6d00ed1dbc5ab04a05.mockapi.io/api/v1/"))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: TodoApp? = null
        val instance get() = _instance!!
    }
}