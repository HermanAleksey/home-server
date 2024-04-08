package com.parokq.plugins.chat.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectedDto(
    @SerialName("dto_type")
    val type: String,
    @SerialName("user_name")
    val userName: String
) : SerializableDto