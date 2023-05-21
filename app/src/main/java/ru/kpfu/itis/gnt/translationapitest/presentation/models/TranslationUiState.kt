package ru.kpfu.itis.gnt.translationapitest.presentation.models

sealed interface TranslationUiState<out T> {
    object Empty: TranslationUiState<Nothing>
    object Loading : TranslationUiState<Nothing>
    class Success<T>(val data: Any?) : TranslationUiState<T>
    class Failure(val error: Throwable) : TranslationUiState<Nothing>
}
