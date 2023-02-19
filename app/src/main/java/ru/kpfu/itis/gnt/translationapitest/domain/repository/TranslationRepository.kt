package ru.kpfu.itis.gnt.translationapitest.domain.repository

import ru.kpfu.itis.gnt.translationapitest.domain.models.Resource
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation

interface TranslationRepository {
    suspend fun getTranslation(data: String): Resource<Translation>
}