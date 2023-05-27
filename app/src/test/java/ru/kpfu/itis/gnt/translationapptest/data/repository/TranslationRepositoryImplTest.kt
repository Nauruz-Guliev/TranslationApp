package ru.kpfu.itis.gnt.translationapptest.data.repository


import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.core.exceptions.AppException
import ru.kpfu.itis.gnt.translationapitest.core.resource.Resource
import ru.kpfu.itis.gnt.translationapitest.data.TemporaryStorage
import ru.kpfu.itis.gnt.translationapitest.data.mappers.TranslationMapper
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi
import ru.kpfu.itis.gnt.translationapitest.data.remote.models.TranslationResponseModel
import ru.kpfu.itis.gnt.translationapitest.data.repository.TranslationRepositoryImpl
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.repository.TranslationRepository
import java.util.*

class TranslationRepositoryImplTest {

    @MockK
    lateinit var translationApiMock: TranslationApi

    private val translationMapper = TranslationMapper()

    lateinit var repository: TranslationRepository

    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val mockWeatherResponse = mockk<TranslationResponseModel>() {
        every { def } returns listOf(mockk {
            every { fl } returns "apple"
            every { pos } returns "noun"
            every { text } returns "яблоко"
            every { tr } returns listOf()
            every { ts } returns "test"
        })
    }

    private val localeFromMock = mockk<Locale> {
        every { language } returns "ru"
    }
    private val localeToMock = mockk<Locale> {
        every { language } returns "en"
    }

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        repository = TranslationRepositoryImpl(
            api = translationApiMock,
            mapper = translationMapper,
            dispatcher = dispatcher
        )
    }

    @Test
    fun `Check default call returns expected kotlin success class with expected data`() {
        runTest(context = dispatcher) {
            // Arrange
            val word = "яблоко"
            val expectedResult = Result.success(
                TranslationUiModel(
                    listOf(
                        TranslationUiModel.Definition(
                            id = 0,
                            fl = "apple",
                            pos = "noun",
                            text = word,
                            tr = listOf(),
                            ts = "test"
                        )
                    )
                )
            )
            coEvery {
                translationApiMock.getTranslation("ru-en", word)
            } returns mockWeatherResponse
            // Act
            val requestResult = repository.getTranslation(localeFromMock, localeToMock, word)
            // Assert
        }
    }

    @Test
    fun `Check when api returns an error kotlin Result failure is returned wtih BackendException`() =
        runTest(dispatcher) {
            // arrange
            val word = "apple"
            val code = 401
            val message = "failed"
            val exception = HttpException(
                Response.error<String>(
                    code, message
                        .toResponseBody("application/json".toMediaType())
                )
            )
            coEvery {
                translationApiMock.getTranslation("ru-en", word)
            } throws exception
            val expectedValue = Result.failure<TranslationUiModel>(
                AppException.BackendException(
                    resource = Resource.String(
                        R.string.error_backend
                    ),
                    cause = exception,
                    code = code
                )
            )
            // Act
            val requestResult = repository.getTranslation(localeFromMock, localeToMock, word)
            // Assert
            assertTrue(expectedValue.isFailure)
            expectedValue.onFailure {
                assertEquals((it as AppException.BackendException).code, code)
            }
            assertEquals(expectedValue, requestResult)
        }

    @Test
    fun `Check when locale format is wrong kotlin failure result is returned with WrongLocaleFormatException`() =
        runTest(dispatcher) {
            // arrange
            val word = "apple"
            val localeWrongMock = mockk<Locale> {
                every { language } returns "!!!!invalid locale!!!!"
            }
            val expectedException = AppException.WrongLocaleFormatException(
                resource = Resource.String(R.string.error_locale),
                cause = Throwable()
            )

            // Act
            val requestResult = repository.getTranslation(localeWrongMock, localeWrongMock, word)
            // Assert
            assertTrue(requestResult.isFailure)
            requestResult.onFailure { exception ->
                assertEquals(exception.javaClass, expectedException.javaClass)
            }
        }

    @Test
    fun `Check when no defintions found returns kotlin Failure class with EmptyResultException`() =
        runTest(dispatcher) {
            // arrange
            val word = "apple"

            coEvery {
                translationApiMock.getTranslation("ru-en", word)
            } returns TranslationResponseModel(def = emptyList())

            // Act
            val requestResult = repository.getTranslation(localeFromMock, localeToMock, word)
            // Assert
            assertTrue(requestResult.isFailure)

            requestResult.onFailure { exception ->
                assertEquals(AppException.EmptyResultException::class.java, exception::class.java)
            }
        }


    @Test
    fun `Check definition by id returns expected data`() =
        runTest(dispatcher) {
            // arrange
            val index = 1000
            // Act
            val requestResult = repository.getDefinitionById(index)
            // Assert
            assertTrue(requestResult.isFailure)
            requestResult.onFailure {exception ->
                assertEquals(exception::class.java, AppException.DataAccessException::class.java)
            }
        }


    @Test
    fun `Check definition by id returns kotlin result failure class when no data`() =
        runTest(dispatcher) {
            // arrange
            val index = 0
            val expectedData = Result.success(translationMapper.toModel(mockWeatherResponse).definition[index])
            // Act
            val requestResult = repository.getDefinitionById(index)
            // Assert
            assertEquals(expectedData, requestResult)
        }


}
