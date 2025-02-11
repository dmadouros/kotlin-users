package me.dmadouros.user.application

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.dmadouros.user.application.plugins.configureDatabase
import me.dmadouros.user.application.plugins.configureRouting
import me.dmadouros.user.application.plugins.configureSerialization
import me.dmadouros.user.domain.User
import me.dmadouros.user.domain.UserFacade
import me.dmadouros.user.domain.UserRepository

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jdbcUrl = System.getenv("DATABASE_URL")

    configureDatabase(jdbcUrl)
    configureSerialization()
    configureRouting(UserFacade(UserRepository()))
}
