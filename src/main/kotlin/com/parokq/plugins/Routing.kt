package com.parokq.plugins

import com.parokq.domain.entity.authentication.InfoResponse
import com.parokq.domain.entity.authentication.LoginResponse
import com.parokq.domain.entity.authentication.RegistrationRequest
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
            val login = call.request.queryParameters["login"]?.toLongOrNull() ?: return@get
            val password = call.request.queryParameters["password"]?.toLongOrNull() ?: return@get

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
        get("auth/request_password_restore") {
            val login = call.request.queryParameters["login"]?.toLongOrNull() ?: return@get

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = null
                )
            )
        }
        get("auth/verify_code") {
            val login = call.request.queryParameters["login"]?.toLongOrNull() ?: return@get
            val code = call.request.queryParameters["code"]?.toLongOrNull() ?: return@get

            //todo add logic
            call.respond(
                InfoResponse(
                    success = true,
                    message = null
                )
            )
        }
        get("auth/reset_password") {
            val login = call.request.queryParameters["login"]?.toLongOrNull() ?: return@get
            val newPassword = call.receive<String>()

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
