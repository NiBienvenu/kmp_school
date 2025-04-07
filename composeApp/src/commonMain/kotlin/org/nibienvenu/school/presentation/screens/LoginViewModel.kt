package org.nibienvenu.school.presentation.screens

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.nibienvenu.school.data.repository.AuthRepository
import org.nibienvenu.school.data.repository.LoginResult
import org.nibienvenu.school.domain.models.User

class LoginViewModel(private val authRepository: AuthRepository) {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val loginState: StateFlow<LoginUiState> = _loginState

    private val _usernameState = MutableStateFlow("")
    val usernameState: StateFlow<String> = _usernameState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    fun onUsernameChanged(username: String) {
        _usernameState.value = username
    }

    fun onPasswordChanged(password: String) {
        _passwordState.value = password
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login() {
        val username = _usernameState.value
        val password = _passwordState.value

        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginUiState.Error("Veuillez remplir tous les champs")
            return
        }

        viewModelScope.launch {
            authRepository.login(username, password).collect { result ->
                _loginState.value = when (result) {
                    is LoginResult.Loading -> LoginUiState.Loading
                    is LoginResult.Success -> LoginUiState.Success(result.user)
                    is LoginResult.Error -> LoginUiState.Error(result.message)
                }
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginUiState.Initial
    }
}

sealed class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}