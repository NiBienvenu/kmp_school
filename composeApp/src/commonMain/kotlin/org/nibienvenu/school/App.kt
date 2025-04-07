package org.nibienvenu.school

import androidx.compose.runtime.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import org.nibienvenu.school.data.repository.AuthRepository
import org.nibienvenu.school.data.remote.KtorAuthClient
import org.nibienvenu.school.data.local.SimpleSessionManager
import org.nibienvenu.school.presentation.viewmodel.LoginViewModel
import org.nibienvenu.school.presentation.screens.LoginScreen
import org.nibienvenu.school.presentation.viewmodel.LoginUiState
import org.nibienvenu.school.ui.theme.SchoolTheme

@Composable
fun App() {

    val baseUrl = "https://school.nibienvenu.org"
    val sessionManager = remember { SimpleSessionManager() }
    val authClient = remember { KtorAuthClient(baseUrl) }
    val authRepository = remember { AuthRepository(authClient, sessionManager) }
    val loginViewModel = remember { LoginViewModel(authRepository) }

    val isLoggedIn by sessionManager.isLoggedIn.collectAsState()


    val loginState by loginViewModel.loginState.collectAsState()

    // Réagir aux changements d'état de connexion
    LaunchedEffect(loginState) {
        if (loginState is LoginUiState.Success) {
            val user = (loginState as LoginUiState.Success).user
            sessionManager.saveToken(user.token)
            sessionManager.saveLoginState(true)
        }
    }

    SchoolTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            if (isLoggedIn) {
                HomeScreen(
                    onLogout = {
                        sessionManager.clearSession()
                    }
                )
            } else {
                LoginScreen(loginViewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(onLogout: () -> Unit) {

    Home(onLogout)
}