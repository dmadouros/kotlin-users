package me.dmadouros.user.application.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import me.dmadouros.user.domain.AddUserCommand
import me.dmadouros.user.domain.AddUserDto
import me.dmadouros.user.domain.UserFacade
import me.dmadouros.user.domain.UserRepository
import java.util.UUID

fun Application.configureRouting(userFacade: UserFacade) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/api/users") {
            val addUserDto: AddUserDto = call.receive<AddUserDto>()

            val addUserCommand = AddUserCommand(UUID.randomUUID(), addUserDto.firstName, addUserDto.lastName)
            val user = userFacade.addUser(addUserCommand)

            call.respond(HttpStatusCode.Created, user)
        }
    }
}
