package me.dmadouros.user.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.mockk
import me.dmadouros.user.application.plugins.configureRouting
import me.dmadouros.user.application.plugins.configureSerialization
import me.dmadouros.user.domain.AddUserDto
import me.dmadouros.user.domain.User
import me.dmadouros.user.domain.UserFacade
import org.junit.jupiter.api.Test
import java.util.UUID

class ApplicationTest {
    @Test
    fun testRoot() =
        testApplication {
            application {
                configureRouting(mockk())
            }
            client.get("/").apply {
                assertThat(status).isEqualTo(HttpStatusCode.OK)
                assertThat(bodyAsText()).isEqualTo("Hello World!")
            }
        }

    @Test
    fun testAddUser() {
        testApplication {
            val client =
                createClient {
                    println(this::class)
                    install(ContentNegotiation) {
                        jackson()
                    }
                }

            val userFacade: UserFacade = mockk()
            application {
                configureSerialization()
                configureRouting(userFacade)
            }

            val user = User(UUID.randomUUID(), "Bilbo", "Baggins")
            coEvery { userFacade.addUser(any()) } returns user

            client.post("/api/users") {
                contentType(ContentType.Application.Json)
                setBody(AddUserDto("Bilbo", "Baggins"))
            }.apply {
                assertThat(status).isEqualTo(HttpStatusCode.Created)
                assertThat(body<User>()).isEqualTo(user)
            }
        }
    }
}
