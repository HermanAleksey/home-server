package com.parokq.plugins.chat.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicKeyDto(
    @SerialName("dto_type")
    val type: String,
    @SerialName("public_key")
    val publicKey: String,
    @SerialName("user_name")
    val userName: String?
) : SerializableDto