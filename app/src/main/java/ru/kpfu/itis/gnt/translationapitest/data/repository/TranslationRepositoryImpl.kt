package ru.kpfu.itis.gnt.translationapitest.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.core.exceptions.AppException
import ru.kpfu.itis.gnt.translationapitest.core.resource.Resource
import ru.kpfu.itis.gnt.translationapitest.core.utils.checkTranslationLocale
import ru.kpfu.itis.gnt.translationapitest.core.utils.wrapRetrofitError
import ru.kpfu.itis.gnt.translationapitest.data.TemporaryStorage
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
            val result = mapper.toModel(wrapRetrofitError {
                api.getTranslation(languages, word)
            })
            TemporaryStorage.definitions = result.definition
            if (result.definition.isNotEmpty()) {
                Result.success(result)
            } else {
                Result.failure(AppException.EmptyResultException(resource = Resource.String(R.string.no_data), Throwable()))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getDefinitionById(id: Int): Result<TranslationUiModel.Definition> {
        return try {
            Result.success(TemporaryStorage.definitions[id])
        } catch (ex: Exception) {
            Result.failure(
                AppException.DataAccessException(
                    resource = Resource.String(R.string.error_data_access),
                    cause = ex
                )
            )
        }
    }

}
