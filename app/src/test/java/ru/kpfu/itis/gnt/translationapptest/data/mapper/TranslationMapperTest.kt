package ru.kpfu.itis.gnt.translationapptest.data.mapper

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import ru.kpfu.itis.gnt.translationapitest.data.mappers.TranslationMapper
import ru.kpfu.itis.gnt.translationapitest.data.remote.models.TranslationResponseModel
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import kotlin.math.exp
import kotlin.test.assertEquals

class TranslationMapperTest {

    private val translationMapper = TranslationMapper()

    private val mockWeatherResponse = mockk<TranslationResponseModel>() {
        every { def } returns listOf(mockk {
            every { fl } returns FAKE_FL
            every { pos } returns FAKE_POS
            every { text } returns FAKE_TEXT
            every { tr } returns listOf(
                TranslationResponseModel.Def.Tr(
                    asp = FAKE_ASP,
                    fr = FAKE_FR,
                    gen = FAKE_GEN,
                    mean = listOf(
                        TranslationResponseModel.Def.Tr.Mean(
                            text = FAKE_TEXT
                        )
                    ),
                    pos = FAKE_POS,
                    syn = listOf(
                        TranslationResponseModel.Def.Tr.Syn(
                            asp = FAKE_ASP,
                            fr = FAKE_FR,
                            gen = FAKE_GEN,
                            pos = FAKE_POS,
                            text = FAKE_TEXT
                        )
                    ),
                    text = FAKE_TEXT
                )
            )
            every { ts } returns FAKE_TS
        })
    }

    @Test
    fun `Check expected data is returned by mapper`() {
        // Arrange
        val expectedData = TranslationUiModel(
            definition = listOf(
                TranslationUiModel.Definition(
                    id = 0,
                    fl = FAKE_FL,
                    pos = FAKE_POS,
                    text = FAKE_TEXT,
                    tr = listOf(
                        TranslationUiModel.Definition.Tr(
                            asp = FAKE_ASP,
                            fr = FAKE_FR,
                            gen = FAKE_GEN,
                            meanings = listOf(
                                FAKE_TEXT
                            ),
                            pos = FAKE_POS,
                            synonyms = listOf(
                                TranslationUiModel.Definition.Tr.Synonyms(
                                    asp = FAKE_ASP,
                                    fr = FAKE_FR,
                                    gen = FAKE_GEN,
                                    pos = FAKE_POS,
                                    text = FAKE_TEXT
                                )
                            ),
                            text = FAKE_TEXT
                        )
                    ),
                    ts = FAKE_TS
                )
            )
        )
        // Act
        val result = translationMapper.toModel(mockWeatherResponse)
        // Assert
        assertEquals(result, expectedData)
    }

    companion object {
        private const val FAKE_FL = "apple"
        private const val FAKE_POS = "noun"
        private const val FAKE_TS = "test"
        private const val FAKE_TEXT = "яблоко"
        private const val FAKE_ASP = "ASP"
        private const val FAKE_FR = 0
        private const val FAKE_GEN = "gen"

    }
}
