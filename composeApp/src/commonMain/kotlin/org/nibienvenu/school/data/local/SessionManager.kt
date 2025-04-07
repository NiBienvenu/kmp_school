package org.nibienvenu.school.data.local

import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isLoggedIn: StateFlow<Boolean>
    fun saveLoginState(isLoggedIn: Boolean)
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearSession()
}