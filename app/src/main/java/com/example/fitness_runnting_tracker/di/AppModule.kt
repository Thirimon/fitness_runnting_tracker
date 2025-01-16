package com.example.fitness_runnting_tracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.fitness_runnting_tracker.db.RunDao
import com.example.fitness_runnting_tracker.db.RunningDatabase
import com.example.fitness_runnting_tracker.other.Constants.Companion.DATABASE_NAME
import com.example.fitness_runnting_tracker.other.Constants.Companion.KEY_FIRST_TIME_TOGGLE
import com.example.fitness_runnting_tracker.other.Constants.Companion.KEY_NAME
import com.example.fitness_runnting_tracker.other.Constants.Companion.KEY_WEIGHT
import com.example.fitness_runnting_tracker.other.Constants.Companion.SHARE_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDb(app:Application):RunningDatabase{
        return Room.databaseBuilder(app,RunningDatabase::class.java,DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideRunDao(db:RunningDatabase):RunDao{
        return db.getRunDao()
    }
    @Singleton
    @Provides
    fun provoidesSharePreferences(app:Application)=app.getSharedPreferences(SHARE_PREFERENCES_NAME,MODE_PRIVATE)
    @Singleton
    @Provides
    fun providesName(sharedPreferences: SharedPreferences)=sharedPreferences.getString(KEY_NAME,"") ?:""

    @Singleton
    @Provides
    fun providesWeight(sharedPreferences: SharedPreferences)=sharedPreferences.getFloat(KEY_WEIGHT,80f)
    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences)=sharedPreferences.getBoolean(
        KEY_FIRST_TIME_TOGGLE,true)

}