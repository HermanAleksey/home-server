package com.parokq

import com.parokq.plugins.configureAuthenticationRouting
import com.parokq.plugins.configureDatabases
import com.parokq.plugins.configurePictureRouting
import com.parokq.plugins.configureSongRouting
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8580, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    configureDatabases()
    configureAuthenticationRouting()
    configurePictureRouting()
    configureSongRouting()
}
