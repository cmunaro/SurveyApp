package com.example.survey.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.survey.di.dataModule
import com.example.survey.di.domainModule
import com.example.survey.di.viewModelModule
import com.example.survey.ui.theme.SurveyTheme
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication

@Serializable
data object MainPageRoute

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(viewModelModule, domainModule, dataModule)
        }
    ) {
        SurveyTheme {
            Scaffold { paddingValues ->

                Navigation(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}