package ru.kpfu.itis.gnt.translationapitest.core.utils

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.core.exceptions.AppException
import ru.kpfu.itis.gnt.translationapitest.core.resource.Resource
import java.io.IOException


suspend fun <T> wrapRetrofitError(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: AppException) {
        throw e
    } catch (e: NullPointerException) {
        throw AppException.EmptyResultException(
            resource = Resource.String(R.string.error_not_found),
            cause = e
        )
    } catch (e: JsonDataException) {
        throw AppException.ParseException(
            cause = e,
            resource = Resource.String(R.string.error_parse)
        )
    } catch (e: JsonEncodingException) {
        throw AppException.ParseException(
            cause = e,
            resource = Resource.String(R.string.error_parse)
        )
    } catch (e: HttpException) {
        throw AppException.BackendException(
            code = e.code(),
            cause = e,
            resource = Resource.String(R.string.error_backend)
        )
    } catch (e: IOException) {
        throw AppException.ConnectionException(
            cause = e,
            resource = Resource.String(R.string.error_connection)
        )
    } catch (e: Exception) {
        throw AppException.DataAccessException(
            cause = e,
            resource = Resource.String(R.string.error_data_access)
        )
    }
}


private val regex = "[a-zA-Z]{2,3}-[a-zA-Z]{2,3}".toRegex()
fun String.checkTranslationLocale(): String {
    if (!this.matches(regex)) {
        throw AppException.WrongLocaleFormatException(
            resource = Resource.String(R.string.error_locale),
            cause = Throwable()
        )
    }
    return this
}

