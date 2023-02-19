package ru.kpfu.itis.gnt.translationapitest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.gnt.translationapitest.presentation.screens.MainScreen
import ru.kpfu.itis.gnt.translationapitest.presentation.ui.theme.TranslationApiTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslationApiTestTheme {
                MainScreen()
            }
        }
    }
}

