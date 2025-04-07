package org.nibienvenu.school.data.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SimpleSessionManager : SessionManager {
    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private var token: String? = null

    override fun saveLoginState(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }

    override fun saveToken(token: String) {
        this.token = token
    }

    override fun getToken(): String? {
        return token
    }

    override fun clearSession() {
        _isLoggedIn.value = false
        token = null
    }
}