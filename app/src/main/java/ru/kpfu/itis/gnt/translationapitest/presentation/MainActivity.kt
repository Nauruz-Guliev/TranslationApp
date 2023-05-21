package ru.kpfu.itis.gnt.translationapitest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.gnt.translationapitest.presentation.navigation.Destinations
import ru.kpfu.itis.gnt.translationapitest.presentation.navigation.NavHost
import ru.kpfu.itis.gnt.translationapitest.presentation.ui.theme.TranslationApiTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TranslationApiTestTheme {
                NavHost(navController = navController)
            }
        }

    }
}


