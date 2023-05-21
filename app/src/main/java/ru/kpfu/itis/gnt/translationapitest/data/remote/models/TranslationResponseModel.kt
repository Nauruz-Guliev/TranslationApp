package ru.kpfu.itis.gnt.translationapitest.data.remote.models


import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class TranslationResponseModel(
    @Json(name = "def")
    val def: List<Def>
) : Parcelable {
    @Parcelize
    data class Def(
        @Json(name = "fl")
        val fl: String?, // flew, flown
        @Json(name = "pos")
        val pos: String, // verb
        @Json(name = "text")
        val text: String, // fly
        @Json(name = "tr")
        val tr: List<Tr>,
        @Json(name = "ts")
        val ts: String? // flaɪ
    ) : Parcelable {
        @Parcelize
        data class Tr(
            @Json(name = "asp")
            val asp: String?, // несов
            @Json(name = "fr")
            val fr: Int?, // 10
            @Json(name = "gen")
            val gen: String?, // ж
            @Json(name = "mean")
            val mean: List<Mean>?,
            @Json(name = "pos")
            val pos: String, // verb
            @Json(name = "syn")
            val syn: List<Syn>?,
            @Json(name = "text")
            val text: String // летать
        ) : Parcelable {
            @Parcelize
            data class Mean(
                @Json(name = "text")
                val text: String // flight
            ) : Parcelable

            @Parcelize
            data class Syn(
                @Json(name = "asp")
                val asp: String?, // сов
                @Json(name = "fr")
                val fr: Int?, // 5
                @Json(name = "gen")
                val gen: String?, // ж
                @Json(name = "pos")
                val pos: String, // verb
                @Json(name = "text")
                val text: String // пролетать
            ) : Parcelable
        }
    }

    @Parcelize
    class Head : Parcelable
}
