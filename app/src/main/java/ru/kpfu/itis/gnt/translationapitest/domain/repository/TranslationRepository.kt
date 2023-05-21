package ru.kpfu.itis.gnt.translationapitest.domain.repository

import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import java.util.Locale

interface TranslationRepository {
    suspend fun getTranslation(from: Locale, to: Locale, word: String): Result<TranslationUiModel>

}
