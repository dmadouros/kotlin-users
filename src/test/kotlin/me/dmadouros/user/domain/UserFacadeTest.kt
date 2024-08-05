package me.dmadouros.user.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.UUID

class UserFacadeTest {
    private val userRepository: UserRepository = mockk()
    private val subject = UserFacade(userRepository)

    @Test
    fun testAddUser() {
        val userId = UUID.randomUUID()

        coJustRun { userRepository.addUser(any()) }

        val addUserCommand = AddUserCommand(userId, "Bilbo", "Baggins")
        val actual = runBlocking { subject.addUser(addUserCommand) }

        assertThat(actual).isEqualTo(User(userId, "Bilbo", "Baggins"))

        coVerify { userRepository.addUser(User(userId, "Bilbo", "Baggins")) }
    }

    @Test
    fun testListUsers() {
        val userId = UUID.randomUUID()

        val user = User(userId, "Bilbo", "Baggins")

        coEvery { userRepository.listUsers() } returns listOf(user)

        val actual = runBlocking { subject.listUsers() }

        assertThat(actual).isEqualTo(listOf(user))

        coVerify { userRepository.listUsers() }
    }

}
