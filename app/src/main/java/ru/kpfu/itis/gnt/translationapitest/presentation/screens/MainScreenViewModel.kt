package ru.kpfu.itis.gnt.translationapitest.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation
import ru.kpfu.itis.gnt.translationapitest.domain.useCases.GetTranslationUseCase
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase
) : ViewModel() {

    private var translationUiState: MutableStateFlow<TranslationUiState<Translation>> =
        MutableStateFlow(TranslationUiState.Empty)

    val state = translationUiState

    fun getTranslation(data: String) {
        translationUiState.value = TranslationUiState.Loading
        viewModelScope.launch {
            val result = getTranslationUseCase(data)
            if (result.message != null) {
                translationUiState.value = TranslationUiState.Failure(Throwable(result.message))
            } else {
                translationUiState.value = TranslationUiState.Success(result.data)
            }
        }
    }
}

