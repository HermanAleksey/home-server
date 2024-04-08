package com.parokq.plugins.chat.model

import com.parokq.plugins.chat.ext.serialize
import com.parokq.plugins.chat.model.dto.SerializableDto
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.send
import java.security.PublicKey
import kotlinx.serialization.json.Json

class Connection(
    val session: DefaultWebSocketSession,
    val userId: Int,
) {
    var publicKey: PublicKey? = null

    suspend fun sendMessage(message: SerializableDto, json: Json) {
        val jsonString = json.serialize(message)
        session.send(jsonString)
    }
}