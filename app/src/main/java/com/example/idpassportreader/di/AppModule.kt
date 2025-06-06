package com.example.idpassportreader.di

import com.example.idpassportreader.datasource.IDPassportDataSource
import com.example.idpassportreader.model.PersonData
import com.example.idpassportreader.repository.IDPassportRepository
import com.example.idpassportreader.retrofit.ApiUtils
import com.example.idpassportreader.retrofit.NFCDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideIDPassportRepository(IPDataSource:IDPassportDataSource): IDPassportRepository{
        return IDPassportRepository(IPDataSource)
    }

    @Provides
    @Singleton
    fun provideIDPassportDataSource(currentPersonData: PersonData,nfcDao: NFCDao): IDPassportDataSource{
        return IDPassportDataSource(currentPersonData,nfcDao)
    }

    @Provides
    @Singleton
    fun providePersonData():PersonData{
        return  PersonData()
    }

    @Provides
    @Singleton
    fun providenfcDao(): NFCDao {
        return  ApiUtils.getNFZDao()
    }
}