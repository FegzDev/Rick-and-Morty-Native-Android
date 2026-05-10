package com.lawpavilion.rick_and_morty.database

import android.content.Context
import androidx.room.Room
import com.lawpavilion.rick_and_morty.core.data.local.utils.LocalDataDefaults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = LocalDataDefaults.DB_NAME
        ).build()
    }
}