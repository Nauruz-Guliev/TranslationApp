package ru.kpfu.itis.gnt.translationapitest.data.models

import com.squareup.moshi.Json

data class TranslationRequest(
    val translateMode: String = "html",
    val platform: String = "api",
    val from: String = "ru",
    val to: String = "en",
    val data: String,
)
