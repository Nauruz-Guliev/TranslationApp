package ru.kpfu.itis.gnt.translationapptest.domain.useCases.main

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import ru.kpfu.itis.gnt.translationapitest.domain.useCases.main.GetTranslationUseCase
import java.util.*
import kotlin.test.assertEquals


class GetTranslationUseCaseTest {

    lateinit var useCase: GetTranslationUseCase

    @MockK
    lateinit var mockRepository: TranslationRepository

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val localeFromMock = mockk<Locale> {
        every { language } returns "ru"
    }
    private val localeToMock = mockk<Locale> {
        every { language } returns "en"
    }


    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        useCase = GetTranslationUseCase(repository = mockRepository)
    }

    @Test
    fun `Check when response is successfull expected value returned`() =
        runTest(dispatcher) {
            //Arrange
            val word = "яблоко"
            val expectedData = mockk<TranslationUiModel>() {
                every { definition } returns listOf(mockk {
                    every { fl } returns "apple"
                    every { pos } returns "noun"
                    every { text } returns "яблоко"
                    every { tr } returns listOf()
                    every { ts } returns "test"
                    every { id } returns 0
                })
            }
            coEvery {
                mockRepository.getTranslation(localeFromMock, localeToMock, word = word)
            } returns Result.success(expectedData)
            //Act
            val result = useCase(from = localeFromMock, to = localeToMock, word = word)
            //Assert
            Assert.assertEquals(result, expectedData)
        }
}
