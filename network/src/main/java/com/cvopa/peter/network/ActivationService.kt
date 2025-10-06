package com.cvopa.peter.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.java


interface ActivationService {
    @GET("/version")
    suspend fun sendActivationCode(
        @Query("code") activationCode: String,
    ): ActivationResponse
}

interface ActivationServiceDataSource {
    suspend fun sendActivationCode(activationCode: String): ActivationResponse
}

@Singleton
class ActivationServiceDataSourceImpl
@Inject
constructor(retrofit: Retrofit) : ActivationServiceDataSource {

    private val service: ActivationService = retrofit.create(ActivationService::class.java)

    override suspend fun sendActivationCode(activationCode: String): ActivationResponse {
        return service.sendActivationCode(activationCode)
    }
}

@JsonClass(generateAdapter = true)
data class ActivationResponse(
    @Json(name = "android")
    val androidVersion: String
)