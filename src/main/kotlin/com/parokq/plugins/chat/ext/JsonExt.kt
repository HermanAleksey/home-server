package com.parokq.plugins.chat.ext

import com.parokq.plugins.chat.model.dto.ConnectedDto
import com.parokq.plugins.chat.model.dto.MessageDataDto
import com.parokq.plugins.chat.model.dto.PublicKeyDto
import com.parokq.plugins.chat.model.dto.SerializableDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Json.deserialize(jsonString: String): SerializableDto? {
    listOf(
        MessageDataDto.serializer(),
        PublicKeyDto.serializer(),
        ConnectedDto.serializer(),
    ).forEach {
        try {
            return decodeFromString(string = jsonString, deserializer = it)
        } catch (_: Exception) {
            println("Error. Couldn't parse object with ${it.javaClass} serializer")
        }
    }
    println("Object cannot be deserialized")
    return null
}

fun Json.serialize(dto: SerializableDto): String {
    return encodeToString(dto)
}