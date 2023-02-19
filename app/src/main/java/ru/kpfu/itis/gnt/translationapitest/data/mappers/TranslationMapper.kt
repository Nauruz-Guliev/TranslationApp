package ru.kpfu.itis.gnt.translationapitest.data.mappers

import ru.kpfu.itis.gnt.translationapitest.data.models.TranslationResponse
import ru.kpfu.itis.gnt.translationapitest.data.models.TranslationRequest
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation

object TranslationMapper {

    fun mapFrom(model: TranslationResponse): Translation {
        return Translation(
            result = model.result
        )
    }

    fun mapTo(model: Translation): TranslationRequest {
        return TranslationRequest(
            data = model.result
        )
    }
}