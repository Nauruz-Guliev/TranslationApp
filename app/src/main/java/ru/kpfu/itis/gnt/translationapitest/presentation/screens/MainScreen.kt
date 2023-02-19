package ru.kpfu.itis.gnt.translationapitest.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.gnt.translationapitest.domain.models.Translation
import ru.kpfu.itis.gnt.translationapitest.presentation.models.TranslationUiState


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        Box {
            Column(
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            ) {
                TranslationResultState(
                    modifier = modifier, viewModel = viewModel
                )
                Row(modifier = modifier.fillMaxWidth()) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = modifier.fillMaxWidth()
                    )
                }
                Row {
                    Button(
                        onClick = {
                            viewModel.getTranslation(text)
                        },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text("Translate")
                    }
                }
            }

        }
    }
}

@Composable
fun TranslationResultState(modifier: Modifier, viewModel: MainScreenViewModel) {
    Row {
        when (val result = viewModel.state.collectAsState().value) {
            is TranslationUiState.Success -> {
                CustomText(
                    modifier = modifier,
                    text = (result.data as Translation).result,
                    fontColor = Color.Green
                )
            }
            is TranslationUiState.Empty -> {
                CustomText(
                    modifier = modifier,
                    text = "Now translation is empty",
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
                CustomText(
                    modifier = modifier,
                    text = result.error.message.toString(),
                    fontColor = Color.Red
                )
            }
        }
    }
}

@Composable
fun CustomText(modifier: Modifier, text: String, fontColor: Color) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = fontColor,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

