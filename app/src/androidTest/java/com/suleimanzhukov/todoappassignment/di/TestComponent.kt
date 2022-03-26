package com.suleimanzhukov.todoappassignment.di

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        FakeModule::class,
        NetworkModule::class
    ]
)
@Singleton
interface TestComponent : AppComponent {

}