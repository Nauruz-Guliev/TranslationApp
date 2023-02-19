package ru.kpfu.itis.gnt.translationapitest.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.kpfu.itis.gnt.translationapitest.data.models.TranslationResponse
import ru.kpfu.itis.gnt.translationapitest.data.models.TranslationRequest


private const val  API_KEY = "a_NB2m2dCemmFYKfgdi2htRHVqB27lnyMCXzcRc9MUjpoR5ZvT4eu2DjIq1AbWGXoa78G6tTYOPq2px9Dm"

interface TranslationApi {

    @Headers(
        "content-type: application/json",
        "accept: application/json",
        "Authorization: $API_KEY")
    @POST("translate")
    fun getTranslation(@Body request: TranslationRequest): Call<TranslationResponse>
}
