
package org.nibienvenu.school.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.nibienvenu.school.domain.models.LoginRequest
import org.nibienvenu.school.domain.models.LoginResponse

class KtorAuthClient(private val baseUrl: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return try {
            val response = client.post {
                url("$baseUrl/auth/login")
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
            response.body<LoginResponse>()
        } catch (e: Exception) {
            LoginResponse(success = false, message = e.message)
        }
    }
}