package com.parokq

import com.parokq.plugins.configureDatabases
import com.parokq.plugins.configureAuthenticationRouting
import com.parokq.plugins.configureSecurity
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8580, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabases()
    configureSecurity()
    configureAuthenticationRouting()
}
