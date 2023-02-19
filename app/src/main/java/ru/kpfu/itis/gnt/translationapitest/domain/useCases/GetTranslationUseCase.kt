package ru.kpfu.itis.gnt.translationapitest.domain.useCases

import ru.kpfu.itis.gnt.translationapitest.domain.models.Resource
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import javax.inject.Inject


class GetTranslationUseCase @Inject constructor(
    private val repository: TranslationRepository
) {
    suspend operator fun invoke(data: String): Resource<Translation> {
        return repository.getTranslation(data)
    }
}