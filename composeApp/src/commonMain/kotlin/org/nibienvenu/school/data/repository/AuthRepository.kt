package org.nibienvenu.school.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.nibienvenu.school.data.remote.KtorAuthClient
import org.nibienvenu.school.domain.models.User

class AuthRepository(private val authClient: KtorAuthClient) {

    private var currentUser: User? = null

    suspend fun login(username: String, password: String): Flow<LoginResult> = flow {
        emit(LoginResult.Loading)

        val response = authClient.login(username, password)

        if (response.success && response.user != null) {
            currentUser = response.user
            emit(LoginResult.Success(response.user))
        } else {
            emit(LoginResult.Error(response.message ?: "Une erreur est survenue"))
        }
    }

    fun getCurrentUser(): User? = currentUser

    fun logout() {
        currentUser = null
    }
}

sealed class LoginResult {
    object Loading : LoginResult()
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}