package ru.kpfu.itis.gnt.translationapitest.domain.models

data class TranslationUiModel(
    val definition: List<Definition>,
) {
    data class Definition(
        val fl: String?,
        val pos: String,
        val text: String,
        val tr: List<Tr>,
        val ts: String?
    ) {
        data class Tr(
            val asp: String?,
            val fr: Int?,
            val gen: String?,
            val meanings: List<String>,
            val pos: String,
            val synonyms: List<Synonyms>,
            val text: String
        ) {
            data class Synonyms(
                val asp: String?,
                val fr: Int?,
                val gen: String?,
                val pos: String,
                val text: String
            )
        }
    }
}
