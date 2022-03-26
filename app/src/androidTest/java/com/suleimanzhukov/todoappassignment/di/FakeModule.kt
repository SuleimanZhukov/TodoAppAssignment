package com.suleimanzhukov.todoappassignment.di

import android.content.Context
import androidx.room.Room
import com.suleimanzhukov.todoappassignment.model.repository.Repository
import com.suleimanzhukov.todoappassignment.repository.FakeRepository
import com.suleimanzhukov.todoappassignment.ui.ListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeModule {

    @Provides
    @Singleton
    fun providesListViewModel(repository: Repository): ListViewModel {
        return ListViewModel(repository)
    }

//    @Provides
//    @Singleton
//    fun providesRepository(retrofit: Retrofit): Repository {
//        return RepositoryImpl(retrofit)
//    }

//    @Provides
//    @Singleton
//    fun providesDatabase(context: Context) = Room.databaseBuilder(
//        context,
//        TaskDatabase::class.java,
//        "task_database"
//    ).build()

    @Provides
    @Singleton
    fun providesFakeRepository(): Repository {
        return FakeRepository()
    }
}