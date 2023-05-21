package ru.kpfu.itis.gnt.translationapitest.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gnt.translationapitest.core.utils.checkTranslationLocale
import ru.kpfu.itis.gnt.translationapitest.core.utils.wrapRetrofitError
import ru.kpfu.itis.gnt.translationapitest.data.mappers.TranslationMapper
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi
import ru.kpfu.itis.gnt.translationapitest.di.qualifiers.IoDispatcher
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import java.util.*
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val api: TranslationApi,
    private val mapper: TranslationMapper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TranslationRepository {
    override suspend fun getTranslation(
        from: Locale,
        to: Locale,
        word: String
    ): Result<TranslationUiModel> = withContext(dispatcher) {
        try {
            val languages =
                (from.language.toString() + "-" + to.language.toString()).checkTranslationLocale()
            val result = wrapRetrofitError {
                api.getTranslation(languages, word)
            }
            Result.success(mapper.toModel(result))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
