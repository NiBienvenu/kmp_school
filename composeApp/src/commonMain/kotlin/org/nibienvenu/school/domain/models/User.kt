package org.nibienvenu.school.domain.models


data class User(
    val id: String,
    val username: String,
    val email: String,
    val token: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val user: User? = null,
    val message: String? = null
)