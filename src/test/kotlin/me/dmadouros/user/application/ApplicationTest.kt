package me.dmadouros.user.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import me.dmadouros.user.application.plugins.configureRouting
import org.junit.jupiter.api.Test

class ApplicationTest {
    @Test
    fun testRoot() =
        testApplication {
            application {
                configureRouting()
            }
            client.get("/").apply {
                assertThat(status).isEqualTo(HttpStatusCode.OK)
                assertThat(bodyAsText()).isEqualTo("Hello World!")
            }
        }
}
