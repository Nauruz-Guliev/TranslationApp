package ru.kpfu.itis.gnt.translationapitest.presentation.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables.RegularText
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables.StringList

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController, id: String?,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    detailsViewModel.getTranslation(Integer.valueOf(id ?: "0"))

    Scaffold(modifier = modifier.fillMaxSize()) {
        it.calculateTopPadding()
        when (val value = detailsViewModel.state.collectAsState().value) {
            is TranslationUiState.Success -> SuccessState(definition = value.data as TranslationUiModel.Definition.Tr)
            is TranslationUiState.Empty -> EmptyState()
            is TranslationUiState.Failure -> ErrorState(error = value.error)
            is TranslationUiState.Loading -> LoadingState()
        }
    }
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    definition: TranslationUiModel.Definition.Tr
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier) {
            RegularText(
                text = definition.text,
                fontSize = dimensionResource(id = R.dimen.font_size_10),
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.size_12)))
            StringList(
                list = definition.synonyms.map { it.text },
                title = stringResource(id = R.string.synonyms)
            )
            StringList(list = definition.meanings, title = stringResource(id = R.string.meaning))
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier = Modifier, error: Throwable) {
    Box(modifier = modifier.fillMaxSize()) {
        RegularText(
            text = stringResource(id = R.string.no_data),
            fontSize = dimensionResource(id = R.dimen.font_size_7),
            fontColor = MaterialTheme.colors.error
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        RegularText(
            text = stringResource(id = R.string.no_data),
            fontSize = dimensionResource(id = R.dimen.font_size_7)
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.size_13))
                .align(Alignment.Center)
        )
    }
}
