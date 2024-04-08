package com.parokq

import com.parokq.plugins.auth.configureAuthenticationRouting
import com.parokq.plugins.chat.configureChatWSRouting
import com.parokq.plugins.music.configureSongRouting
import com.parokq.plugins.picture.configurePictureRouting
import io.ktor.serialization.gson.gson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import java.time.Duration


fun main() {
    embeddedServer(Netty, port = 8580, host = "192.168.100.22", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    configureChatWSRouting()
//    configureDatabases()
    configureAuthenticationRouting()
    configurePictureRouting()
    configureSongRouting()
}