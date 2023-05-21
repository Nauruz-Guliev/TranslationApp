package ru.kpfu.itis.gnt.translationapitest.data.mappers

import ru.kpfu.itis.gnt.translationapitest.data.remote.models.TranslationResponseModel
import ru.kpfu.itis.gnt.translationapitest.domain.mapper.BaseMapper
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import javax.inject.Inject

class TranslationMapper @Inject constructor() :
    BaseMapper<TranslationUiModel, TranslationResponseModel> {
    override fun toModel(model: TranslationResponseModel): TranslationUiModel = with(model) {
        TranslationUiModel(
            definition = def.map { definition ->
                with(definition) {
                    TranslationUiModel.Definition(
                        fl = fl,
                        pos = pos,
                        text = text,
                        tr = tr.map { tree ->
                            with(tree) {
                                TranslationUiModel.Definition.Tr(
                                    asp = asp,
                                    fr = fr,
                                    gen = gen,
                                    meanings = mean?.map { meaning ->
                                        meaning.text
                                    } ?: listOf(),
                                    pos = pos,
                                    synonyms = syn?.map { synonym ->
                                        with(synonym) {
                                            TranslationUiModel.Definition.Tr.Synonyms(
                                                asp = asp,
                                                fr = fr,
                                                gen = gen,
                                                pos = pos,
                                                text = text
                                            )
                                        }
                                    } ?: listOf(),
                                    text = text
                                )
                            }
                        },
                        ts = ts
                    )
                }
            }
        )
    }

    override fun fromModem(model: TranslationUiModel): TranslationResponseModel? {
        return null
    }


}
