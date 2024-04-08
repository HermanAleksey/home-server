package com.parokq.plugins.chat.templates

import com.parokq.plugins.chat.ext.splitBySize
import com.parokq.plugins.chat.model.dto.MessageDataDto
import com.parokq.plugins.chat.model.dto.PublicKeyDto
import java.text.SimpleDateFormat
import java.util.Date


private fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm")
    val date = Date()
    return sdf.format(date)
}

fun getPublicKeyMessage(publicKey: String, userName: String): PublicKeyDto {
    return PublicKeyDto(
        type = "public_key",
        publicKey = publicKey,
        userName = userName,
    )
}

fun getGreetingsMessage(userName: String): MessageDataDto {
    return createMessageDto(
        message = "Hello! \nWelcome to the chat, buddy! \nYour number is `${userName}`",
        userName = userName
    )
}

fun createMessageDto(message: String, userName: String): MessageDataDto {
    return MessageDataDto(
        type = "message",
        text = message.splitBySize().map { it.toByteArray() },
        userName = userName,
        time = getCurrentTime()
    )
}

fun getDisconnectedMessage(userName: String): MessageDataDto {
    return MessageDataDto(
        type = "message",
        text = listOf(
            "Our friend with number '$userName` has left the chat".toByteArray()
        ),
        userName = "-1",
        time = getCurrentTime()
    )
}