package com.parokq.plugins.chat.ext

import com.parokq.plugins.chat.model.Connection
import io.ktor.websocket.send

suspend fun Set<Connection>.sendToAll(text: String) {
    this.forEach {
        it.session.send(text)
    }
}