package com.parokq.plugins.chat.model

import com.parokq.plugins.chat.ext.serialize
import com.parokq.plugins.chat.model.dto.MessageDataDto
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.send
import java.security.PublicKey
import kotlinx.serialization.json.Json

class Connection(
    val session: DefaultWebSocketSession,
    val userId: Int,
) {
    var publicKey: PublicKey? = null

    suspend fun sendMessage(messageObject: MessageDataDto, json: Json) {
        val jsonString = json.serialize(messageObject)
        session.send(jsonString)
    }
}