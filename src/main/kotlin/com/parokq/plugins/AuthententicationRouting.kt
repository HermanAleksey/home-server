package com.parokq.plugins

import com.parokq.domain.entity.authentication.*
import io.ktor.serialization.gson.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*

fun Application.configureAuthenticationRouting() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("auth/login") {
            val request = call.receive<LoginRequest>()

            //todo add processing
            //return LoginResponse
            val response = LoginResponse(
                userName = "uName",
                photo = "qweqe",
                placeOfResidence = "qwe",
                otherData = "fwedfq"
            )
            call.respond(response)
        }
        post("auth/registration") {
            val request = call.receive<RegistrationRequest>()

            println(request)

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = request.toString()
                )
            )
        }
        get("auth/restore") {
            val request = call.receive<RestoreRequest>()

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = null
                )
            )
        }
        get("auth/code") {
            val request = call.receive<VerifyCodeRequest>()

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = null
                )
            )
        }
        post("auth/reset") {
            val newPassword = call.receive<ResetPasswordRequest>()

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = null
                )
            )
        }
    }
}
