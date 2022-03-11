package com.rogergcc.nearplacestest99minutes.di

import com.google.gson.GsonBuilder
import com.rogergcc.nearplacestest99minutes.application.AppConstants
import com.rogergcc.nearplacestest99minutes.data.local.LocalFavoritesPlacesDataSource
import com.rogergcc.nearplacestest99minutes.data.remote.RemotePlacesDataSource
import com.rogergcc.nearplacestest99minutes.domain.PlaceRepository
import com.rogergcc.nearplacestest99minutes.domain.PlacesRepositoryImpl
import com.rogergcc.nearplacestest99minutes.domain.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)

    @Singleton
    @Provides
    fun provideProductRepository(
        dataSourceRemote: RemotePlacesDataSource,
        dataSourceLocal: LocalFavoritesPlacesDataSource,
    ): PlaceRepository {
        return PlacesRepositoryImpl(
            dataSourceRemote, dataSourceLocal
        )
    }



}

