package me.dmadouros.user.domain

import java.util.UUID

data class AddUserCommand(val userId: UUID, val firstName: String, val lastName: String)