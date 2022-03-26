package com.suleimanzhukov.todoappassignment.di

import com.suleimanzhukov.todoappassignment.ui.DialogFragment
import com.suleimanzhukov.todoappassignment.ui.DialogFragmentEdit
import com.suleimanzhukov.todoappassignment.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ContextModule::class,
        Module::class,
        NetworkModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(dialogFragment: DialogFragment)

    fun inject(dialogFragmentEdit: DialogFragmentEdit)
}