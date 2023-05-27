package ru.kpfu.itis.gnt.translationapitest.core.exceptions

import ru.kpfu.itis.gnt.translationapitest.core.resource.Resource

sealed class AppException(open val resource: Resource.String) : RuntimeException() {
    data class ParseException(override val resource: Resource.String, override val cause: Throwable): AppException(resource)
    data class BackendException(override val resource: Resource.String, override val cause: Throwable, val code: Int): AppException(resource)
    data class ConnectionException(override val resource: Resource.String, override val cause: Throwable): AppException(resource)
    data class EmptyResultException(override val resource: Resource.String, override val cause: Throwable): AppException(resource)
    data class DataAccessException(override val resource: Resource.String, override val cause: Throwable): AppException(resource)
    data class WrongLocaleFormatException(override val resource: Resource.String, override val cause: Throwable): AppException(resource)

}
