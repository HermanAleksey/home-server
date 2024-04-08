package com.parokq.plugins.chat.model

import com.parokq.plugins.chat.Encoder
import com.parokq.plugins.chat.ext.splitBySize
import com.parokq.plugins.chat.model.dto.MessageDataDto
import com.parokq.plugins.chat.model.dto.PublicKeyDto
import com.parokq.plugins.chat.model.dto.encodeContent
import java.security.PublicKey
import java.text.SimpleDateFormat
import java.util.Date

class MessageFactory(
    private val encoder: Encoder
) {

    fun createPublicKeyMessage(publicKey: PublicKey, userName: String): PublicKeyDto {
        val publicKeyAsString = encoder.encryptPublicKey(publicKey)

        return PublicKeyDto(
            type = "public_key",
            publicKey = publicKeyAsString,
            userName = userName,
        )
    }

    fun createGreetingsMessage(userName: String): MessageDataDto {
        return createMessage(
            message = "Hello! \nWelcome to the chat, buddy! \nYour number is `${userName}`",
        )
    }

    fun createDisconnectedMessage(userName: String, connectionPublicKey: PublicKey): MessageDataDto {
        return createMessage(
            message = "Our friend with number '$userName` has left the chat"
        ).encodeContent(
            encoder = encoder,
            publicKey = connectionPublicKey
        )
    }

    private fun createMessage(message: String): MessageDataDto {
        return MessageDataDto(
            type = "message",
            text = message.splitBySize().map { it.toByteArray() },
            userName = "A",
            time = getCurrentTime()
        )
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date()
        return sdf.format(date)
    }
}