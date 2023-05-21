package ru.kpfu.itis.gnt.translationapitest.domain.useCases.details

import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import javax.inject.Inject

class GetDefinitionByIdUseCase @Inject constructor(
    private val repository: TranslationRepository,
) {
    suspend operator fun invoke(
        id: Int
    ): Result<TranslationUiModel.Definition> {
        return repository.getDefinitionById(
            id = id
        )
    }
}

