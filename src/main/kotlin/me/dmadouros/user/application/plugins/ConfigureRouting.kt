package me.dmadouros.user.application.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.http.content.staticFiles
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.velocity.VelocityContent
import me.dmadouros.user.domain.AddUserCommand
import me.dmadouros.user.domain.AddUserDto
import me.dmadouros.user.domain.User
import me.dmadouros.user.domain.UserFacade
import java.io.File
import java.util.UUID

fun Application.configureRouting(userFacade: UserFacade) {
    routing {
        staticFiles("/", File("ui"))
        staticFiles("/assets", File("ui/assets"))

        post("/api/users") {
            val addUserDto: AddUserDto = call.receive<AddUserDto>()

            val addUserCommand = AddUserCommand(UUID.randomUUID(), addUserDto.firstName, addUserDto.lastName)
            val user = userFacade.addUser(addUserCommand)

            call.respond(HttpStatusCode.Created, user)
        }

        get("/api/users") {
            val users = userFacade.listUsers()

            call.respond(HttpStatusCode.OK, users)
        }

        get("/users") {
            val users = userFacade.listUsers()

            call.respond(FreeMarkerContent("users.ftl", mapOf("users" to users)))
        }
    }
}
