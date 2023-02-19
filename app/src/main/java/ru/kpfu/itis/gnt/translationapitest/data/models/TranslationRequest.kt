package ru.kpfu.itis.gnt.translationapitest.data.models

import com.squareup.moshi.Json

data class TranslationRequest(
    @Json(name = "translateMode")
    val translateMode: String = "html",

    @Json(name = "platform")
    val platform: String = "api",

    @Json(name = "from")
    val from: String = "ru",

    @Json(name = "to")
    val to: String = "en",

    @Json(name = "data")
    val data: String,
)
