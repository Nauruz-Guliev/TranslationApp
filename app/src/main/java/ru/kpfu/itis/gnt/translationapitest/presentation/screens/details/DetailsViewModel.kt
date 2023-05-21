package ru.kpfu.itis.gnt.translationapitest.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.domain.useCases.details.GetDefinitionByIdUseCase
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDefinitionByIdUseCase: GetDefinitionByIdUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<TranslationUiState<TranslationUiModel.Definition.Tr>> =
        MutableStateFlow(TranslationUiState.Empty)
    val state = _state.asStateFlow()

    fun getTranslation(id: Int) {
        viewModelScope.launch {
            _state.emit(TranslationUiState.Loading)
            getDefinitionByIdUseCase(
                id = id
            ).onSuccess { model ->
                _state.emit(TranslationUiState.Success(model.tr[0]))
            }.onFailure { error ->
                _state.emit(TranslationUiState.Failure(error))
            }
        }
    }
}
