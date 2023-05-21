package ru.kpfu.itis.gnt.translationapitest.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.useCases.main.GetTranslationUseCase
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState
import java.util.*
import javax.inject.Inject

typealias TranslationState = MutableStateFlow<TranslationUiState<TranslationUiModel>>
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase
) : ViewModel() {

    private val _state: TranslationState =
        MutableStateFlow(TranslationUiState.Empty)
    val state = _state.asStateFlow()

    fun getTranslation(from: Locale, to: Locale, word: String) {
        viewModelScope.launch {
            _state.emit(TranslationUiState.Loading)
            getTranslationUseCase(
                from = from,
                to = to,
                word = word
            ).onSuccess { translationModel ->
                _state.emit(TranslationUiState.Success(translationModel))
            }.onFailure { error ->
                _state.emit(TranslationUiState.Failure(error))
            }
        }
    }
}

