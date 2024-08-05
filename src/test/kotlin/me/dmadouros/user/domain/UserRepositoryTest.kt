package me.dmadouros.user.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import me.dmadouros.user.application.plugins.configureDatabase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.util.UUID

class UserRepositoryTest {
    private val subject = UserRepository()

    @Container
    val dbContainer =
        PostgreSQLContainer<Nothing>("postgres:16-bookworm").apply {
            withDatabaseName("nutrisystem")
            withUsername("test")
            withPassword("test")
            start()
        }

    @BeforeEach
    fun setUp() {
        val jdbcUrl =
            "${dbContainer.jdbcUrl}&user=${dbContainer.username}&password=${dbContainer.password}"
        configureDatabase(jdbcUrl)
    }

    @AfterEach
    fun tearDown() {
        dbContainer.stop()
    }

    @Test
    fun `addUser`() {
        val user = User(UUID.randomUUID(), "Bilbo", "Baggins")
        runBlocking { subject.addUser(user) }

        val actual: List<User> = runBlocking { subject.listUsers() }

        assertThat(actual).isEqualTo(listOf(user))
    }

    @Test
    fun `listUsers`() {
        val actual = runBlocking { subject.listUsers() }

        assertThat(actual).isEqualTo(emptyList())
    }
}