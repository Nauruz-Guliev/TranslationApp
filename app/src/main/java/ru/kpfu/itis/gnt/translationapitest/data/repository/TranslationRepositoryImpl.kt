package ru.kpfu.itis.gnt.translationapitest.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gnt.translationapitest.data.mappers.TranslationMapper
import ru.kpfu.itis.gnt.translationapitest.data.models.TranslationRequest
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi
import ru.kpfu.itis.gnt.translationapitest.domain.models.Resource
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val api: TranslationApi,
    private val dispatcher: CoroutineDispatcher
) : TranslationRepository {

    override suspend fun getTranslation(data: String): Resource<Translation> =
        withContext(dispatcher) {
            return@withContext try {
                val response = api.getTranslation(
                    request = TranslationRequest(
                        data = data
                    )
                ).execute().body()
                    ?: return@withContext Resource.Error(message = "Unable to retrieve the translation")
                if (response.err != null) return@withContext Resource.Error(message = response.err.toString())
                Resource.Success(
                    data = TranslationMapper.mapFrom(
                        model = response
                    )
                )
            } catch (e: Exception) {
                Resource.Error(message = e.message ?: " Unknown error")
            }
        }
}
