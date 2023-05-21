package ru.kpfu.itis.gnt.translationapitest.data.remote

import retrofit2.http.POST
import retrofit2.http.Query
import ru.kpfu.itis.gnt.translationapitest.data.remote.models.TranslationResponseModel


interface TranslationApi {
    @POST("api/v1/dicservice.json/lookup")
    suspend fun getTranslation(
        @Query("lang") language: String,
        @Query("text") word: String
    ): TranslationResponseModel

}
