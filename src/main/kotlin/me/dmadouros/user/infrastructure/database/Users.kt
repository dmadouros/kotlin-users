package me.dmadouros.user.infrastructure.database

import org.jetbrains.exposed.sql.Table

class Users : Table() {
    private val id = uuid("id")
    private val firstName = varchar("firstName", length = 50)
    private val lastName = varchar("lastName", length = 50)

//    suspend fun <T> dbQuery(block: suspend () -> T): T =
//        newSuspendedTransaction(Dispatchers.IO) { block() }
}
