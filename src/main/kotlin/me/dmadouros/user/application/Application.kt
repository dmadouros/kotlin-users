package me.dmadouros.user.application

import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.freemarker.FreeMarker
import io.ktor.server.netty.Netty
import me.dmadouros.user.application.plugins.configureDatabase
import me.dmadouros.user.application.plugins.configureRouting
import me.dmadouros.user.application.plugins.configureSerialization
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
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    configureRouting(UserFacade(UserRepository()))
}
