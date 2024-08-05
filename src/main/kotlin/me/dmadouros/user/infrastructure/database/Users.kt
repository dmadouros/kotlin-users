package me.dmadouros.user.infrastructure.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    val userId = uuid("id")
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", length = 50)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
//                addLogger(StdOutSqlLogger)
                block()
            }
        }
}
