package com.fab.home.data.service

import com.fab.home.BuildConfig
import com.fab.home.core.AppConstants
import com.fab.home.data.model.response.AbilityList
import com.fab.home.data.model.response.EvolutionChain
import com.fab.home.data.model.response.PokemonResults
import com.fab.home.data.model.response.Specie
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface WebService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
    ): PokemonResults

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String
    ): Specie

    @GET
    suspend fun getEvolutionChain(
        @Url url: String
    ): EvolutionChain

    @GET("pokemon/{name}")
    suspend fun getAbilityList(
        @Path("name") name: String
    ): AbilityList
}

object RetrofitClient {

    val webService by lazy {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(logging)
        }

        Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}