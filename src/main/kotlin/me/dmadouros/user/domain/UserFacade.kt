package me.dmadouros.user.domain

class UserFacade(private val userRepository: UserRepository) {
    suspend fun addUser(addUserCommand: AddUserCommand): User {
        val user =
            User(
                userId = addUserCommand.userId,
                firstName = addUserCommand.firstName,
                lastName = addUserCommand.lastName,
            )

        userRepository.addUser(user)

        return user
    }
}
