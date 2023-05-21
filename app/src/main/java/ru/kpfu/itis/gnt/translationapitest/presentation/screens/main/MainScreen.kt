package ru.kpfu.itis.gnt.translationapitest.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.kpfu.itis.gnt.translationapitest.R
import ru.kpfu.itis.gnt.translationapitest.core.exceptions.AppException
import ru.kpfu.itis.gnt.translationapitest.domain.models.TranslationUiModel
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState
import ru.kpfu.itis.gnt.translationapitest.presentation.navigation.Destinations
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables.RegularText
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.composables.TranslationList
import java.util.*


val translationAreaSize = 420.dp

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    var word by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val localeTo = context.resources.configuration.locales[0]
    val localeFrom = Locale.ENGLISH

    Scaffold(modifier = Modifier.fillMaxSize()) {
        it.calculateBottomPadding()
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TranslationResultState(
                modifier = modifier.height(translationAreaSize), viewModel = viewModel,
                navController = navController
            )
            Column(
                modifier = modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.size_8),
                        end = dimensionResource(id = R.dimen.size_8),
                        bottom = dimensionResource(id = R.dimen.size_140)
                    )
            ) {
                TextField(
                    value = word,
                    onValueChange = { word = it },
                    modifier = modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        viewModel.getTranslation(
                            from = localeFrom,
                            to = localeTo,
                            word = word
                        )
                    },
                    modifier = modifier.fillMaxWidth()
                ) {
                    RegularText(
                        text = stringResource(id = R.string.translate),
                        fontColor = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun TranslationResultState(
    modifier: Modifier,
    viewModel: MainScreenViewModel,
    navController: NavController
) {
    Row {
        when (val result = viewModel.state.collectAsState().value) {
            is TranslationUiState.Success -> {
                TranslationList(
                    list = (result.data as TranslationUiModel).definition,
                    title = stringResource(id = R.string.definition),
                ) { id ->
                    navController.navigate(Destinations.DETAILS + "$id")
                }
            }
            is TranslationUiState.Empty -> {
                RegularText(
                    modifier = modifier,
                    text = stringResource(id = R.string.start_translating),
                    fontColor = Color.Black
                )
            }
            is TranslationUiState.Loading -> {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.padding(24.dp)
                    )
                }
            }
            is TranslationUiState.Failure -> {
                when(result.error) {
                    is AppException.BackendException -> {
                        Column {
                            RegularText(
                                modifier = modifier,
                                text = (result.error).resource.getValue(),
                                fontColor = Color.Black
                            )
                            RegularText(
                                modifier = modifier,
                                text = (result.error).code.toString(),
                                fontColor = Color.Red,
                                fontSize = dimensionResource(id = R.dimen.font_size_10),
                            )
                            RegularText(
                                modifier = modifier,
                                text = (result.error).cause.message.toString(),
                                fontColor = Color.Black,
                                fontSize = dimensionResource(id = R.dimen.font_size_7),
                            )
                        }
                    }
                    is AppException -> {
                        RegularText(
                            modifier = modifier,
                            text = (result.error).resource.getValue(),
                            fontColor = Color.Red
                        )
                    }
                }
            }
        }
    }
}


