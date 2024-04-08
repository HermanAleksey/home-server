package com.parokq.plugins.chat.model.dto

import com.parokq.plugins.chat.Encoder
import java.security.PrivateKey
import java.security.PublicKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDataDto(
    @SerialName("dto_type")
    val type: String,
    @SerialName("text")
    val text: List<ByteArray>,
    @SerialName("user_name")
    val userName: String,
    @SerialName("time")
    val time: String,
) : SerializableDto

fun MessageDataDto.encodeContent(encoder: Encoder, publicKey: PublicKey): MessageDataDto {
    return copy(
        text = this.text.map {
            encoder.encryptMessage(message = it, publicKey = publicKey)
        }
    )
}

fun MessageDataDto.decodeContent(encoder: Encoder, privateKey: PrivateKey): MessageDataDto {
    return copy(
        text = this.text.map {
            encoder.decryptMessage(message = it, privateKey = privateKey)
        }
    )
}