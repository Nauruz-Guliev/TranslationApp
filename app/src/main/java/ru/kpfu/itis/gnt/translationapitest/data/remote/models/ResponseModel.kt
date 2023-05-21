package ru.kpfu.itis.gnt.translationapitest.data.remote.models


import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseModel(
    @Json(name = "def")
    val def: List<Def>,
) : Parcelable {
    @Parcelize
    data class Def(
        @Json(name = "anm")
        val anm: String,
        @Json(name = "gen")
        val gen: String,
        @Json(name = "pos")
        val pos: String,
        @Json(name = "text")
        val text: String,
        @Json(name = "tr")
        val tr: List<Tr>
    ) : Parcelable {
        @Parcelize
        data class Tr(
            @Json(name = "fr")
            val fr: Int,
            @Json(name = "mean")
            val mean: List<Mean>?,
            @Json(name = "pos")
            val pos: String,
            @Json(name = "syn")
            val syn: List<Syn>?,
            @Json(name = "text")
            val text: String
        ) : Parcelable {
            @Parcelize
            data class Mean(
                @Json(name = "text")
                val text: String
            ) : Parcelable

            @Parcelize
            data class Syn(
                @Json(name = "fr")
                val fr: Int,
                @Json(name = "pos")
                val pos: String,
                @Json(name = "text")
                val text: String
            ) : Parcelable
        }
    }
}
