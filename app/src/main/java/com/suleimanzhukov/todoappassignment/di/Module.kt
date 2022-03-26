package com.suleimanzhukov.todoappassignment.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.suleimanzhukov.todoappassignment.model.repository.Repository
import com.suleimanzhukov.todoappassignment.model.repository.RepositoryImpl
//import com.suleimanzhukov.todoappassignment.model.repository.RoomRepository
//import com.suleimanzhukov.todoappassignment.model.rest.TaskDatabase
import com.suleimanzhukov.todoappassignment.ui.ListAdapter
import com.suleimanzhukov.todoappassignment.ui.ListViewModel
import com.suleimanzhukov.todoappassignment.ui.MainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class Module {

    @Provides
    @Singleton
    fun providesListViewModel(repository: Repository): ListViewModel {
        return ListViewModel(repository)
    }

    @Provides
    @Singleton
    fun providesRepository(retrofit: Retrofit): Repository {
        return RepositoryImpl(retrofit)
    }

//    @Provides
//    @Singleton
//    fun providesDatabase(context: Context) = Room.databaseBuilder(
//            context,
//            TaskDatabase::class.java,
//            "task_database")
//            .build()
//
//    @Provides
//    @Singleton
//    fun providesRoomRepository(taskDatabase: TaskDatabase): Repository {
//        return RoomRepository(taskDatabase)
//    }
}