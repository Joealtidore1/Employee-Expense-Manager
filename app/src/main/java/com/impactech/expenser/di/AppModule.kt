package com.impactech.expenser.di

import android.app.Application
import androidx.room.Room
import com.impactech.expenser.data.local.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): ExpenseDatabase{
        return  Room.databaseBuilder(
            app,
            ExpenseDatabase::class.java,
            "lagos_revenue.db"
        ).build()

    }
}