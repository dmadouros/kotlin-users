package me.dmadouros.user.domain

import java.util.UUID

data class User(val userId: UUID, val firstName: String, val lastName: String)