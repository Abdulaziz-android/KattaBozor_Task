package com.kattabozor.task.di.component

import com.kattabozor.task.di.module.DatabaseModule
import com.kattabozor.task.di.module.NetworkModule
import com.kattabozor.task.di.module.RepositoryImplModule
import com.kattabozor.task.di.module.UseCaseModule
import dagger.Component

@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryImplModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent