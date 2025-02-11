package com.example.neurotest

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.neurotest.data.AppDatabase
import com.example.neurotest.data.DayResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Указывает, что зависимости будут существовать в течение жизни приложения
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "stats_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideTestResultDao(database: AppDatabase): DayResultDao {
        return database.testResultDao()
    }
}