package ru.kpfu.itis.gnt.translationapitest.domain.useCases.main

import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import java.util.*
import javax.inject.Inject


class GetTranslationUseCase @Inject constructor(
    private val repository: TranslationRepository,
) {
    suspend operator fun invoke(
        from: Locale,
        to: Locale,
        word: String
    ): Result<TranslationUiModel> {
        return repository.getTranslation(
            from = from,
            to = to,
            word = word
        )
    }
}
