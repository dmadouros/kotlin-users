package me.dmadouros.user.domain

import me.dmadouros.user.infrastructure.database.Users
import me.dmadouros.user.infrastructure.database.Users.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.time.Instant

class UserRepository {
    suspend fun addUser(user: User) {
        dbQuery {
            val now = Instant.now()
            Users.insert { row ->
                row[userId] = user.userId
                row[firstName] = user.firstName
                row[lastName] = user.lastName
                row[createdAt] = now
                row[updatedAt] = now
            }
        }
    }

    suspend fun listUsers(): List<User> {
        return dbQuery {
            Users.selectAll()
                .map { row ->
                    User(
                        userId = row[Users.userId],
                        firstName = row[Users.firstName],
                        lastName = row[Users.lastName],
                    )
                }
        }
    }
}
