package com.parokq.plugins.auth

import com.parokq.domain.entity.authentication.InfoResponse
import com.parokq.domain.entity.authentication.LoginRequest
import com.parokq.domain.entity.authentication.LoginResponse
import com.parokq.domain.entity.authentication.RegistrationRequest
import com.parokq.domain.entity.authentication.ResetPasswordRequest
import com.parokq.domain.entity.authentication.RestorePasswordRequest
import com.parokq.domain.entity.authentication.VerifyCodeRequest
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureAuthenticationRouting() {
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
            val request = call.receive<RestorePasswordRequest>()

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
