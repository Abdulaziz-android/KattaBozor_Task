package com.kattabozor.task.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.kattabozor.task.common.Constants
import com.kattabozor.task.data.local.AppDatabase
import com.kattabozor.task.data.local.dao.OfferDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(Constants.SHARED_NAME, MODE_PRIVATE)
    }

    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE
        ).allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideOfferDao(database: AppDatabase): OfferDao {
        return database.offerDao
    }

}