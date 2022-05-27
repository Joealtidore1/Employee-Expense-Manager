package com.impactech.expenser.di

import com.impactech.expenser.data.repository.ExpenseRepositoryImpl
import com.impactech.expenser.domain.ExpenseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ExpenserRepositoryModule {
    @Binds
    @Singleton
    abstract fun provideExpenserRepository(repository: ExpenseRepositoryImpl): ExpenseInterface
}