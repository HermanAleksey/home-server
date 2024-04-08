package com.parokq.plugins.chat

import com.parokq.plugins.chat.UserIdCounter.generateUserId
import com.parokq.plugins.chat.ext.deserialize
import com.parokq.plugins.chat.model.Connection
import com.parokq.plugins.chat.model.MessageFactory
import com.parokq.plugins.chat.model.dto.MessageDataDto
import com.parokq.plugins.chat.model.dto.PublicKeyDto
import com.parokq.plugins.chat.model.dto.SerializableDto
import com.parokq.plugins.chat.model.dto.decodeContent
import com.parokq.plugins.chat.model.dto.encodeContent
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import java.security.PrivateKey
import java.util.Collections
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

fun Application.configureChatWSRouting() {
    routing {
        val keyFactory = KeyFactory()
        val encoder = Encoder()
        val messageFactory = MessageFactory(encoder)
        val (publicServerKey, privateServerKey) = keyFactory.generateKeyPair()

        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                polymorphic(SerializableDto::class) {
                    subclass(PublicKeyDto::class)
                    subclass(MessageDataDto::class)
                }
            }
        }

        val allConnections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat") {
            val thisUserId = generateUserId()
            val thisConnection = Connection(session = this, userId = thisUserId)
            allConnections += thisConnection

            try {
                val publicKeyMessage = messageFactory.createPublicKeyMessage(
                    publicKey = publicServerKey,
                    userName = thisConnection.userId.toString()
                )
                thisConnection.sendMessage(publicKeyMessage, json = json)

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val text = frame.readText()

                    val incomingObject = json.deserialize(text) ?: continue
                    println("Incoming object: $incomingObject, \n class:${incomingObject.javaClass.simpleName}")

                    if (thisConnection.publicKey == null) {
                        tryProcessClientPublicKey(
                            incomingObject = incomingObject,
                            clientConnection = thisConnection,
                            json = json,
                            encoder = encoder,
                            messageFactory = messageFactory
                        )
                    } else {
                        tryConsumeClientMessage(
                            incomingObject = incomingObject,
                            encoder = encoder,
                            privateServerKey = privateServerKey,
                            allConnections = allConnections,
                            json = json
                        )
                    }
                }
            } catch (e: Exception) {
                println("Error occurred: ${e.localizedMessage}")
                e.printStackTrace()
            } finally {
                println("Disconnecting user: $thisUserId")
                // disconnect user and notify others
                allConnections -= thisConnection

                allConnections.forEach { connection ->
                    val pbk = connection.publicKey ?: return@forEach
                    val disconnectedMessage = messageFactory.createDisconnectedMessage(
                        userName = thisUserId.toString(),
                        connectionPublicKey = pbk
                    )
                    connection.sendMessage(disconnectedMessage, json)
                }
            }
        }
    }
}

private suspend fun tryProcessClientPublicKey(
    incomingObject: SerializableDto,
    clientConnection: Connection,
    json: Json,
    encoder: Encoder,
    messageFactory: MessageFactory,
) {
    // if public key of user is empty - expect that message is PublicKeyDto, not encrypted
    when (incomingObject) {
        is PublicKeyDto -> {
            val userPublicKey = encoder.decryptPublicKey(incomingObject.publicKey)
            clientConnection.publicKey = userPublicKey

            // send greetings message
            val helloMessage = messageFactory
                .createGreetingsMessage(clientConnection.userId.toString())
                .encodeContent(
                    encoder = encoder,
                    publicKey = userPublicKey
                )
            clientConnection.sendMessage(helloMessage, json)
        }

        else -> {
            println("Error. Not a public key")
        }
    }
}

private suspend fun tryConsumeClientMessage(
    incomingObject: SerializableDto,
    allConnections: MutableSet<Connection>,
    privateServerKey: PrivateKey,
    encoder: Encoder,
    json: Json,
) {
    when (incomingObject) {
        is MessageDataDto -> {
            val decodedMessageObject = incomingObject.decodeContent(
                encoder = encoder,
                privateKey = privateServerKey
            )

            // send message to all other users, encoded
            allConnections.forEach { connection ->
                val pbk = connection.publicKey ?: return@forEach

                val messageEncodedForUser = decodedMessageObject.encodeContent(
                    encoder = encoder,
                    publicKey = pbk
                )
                connection.sendMessage(messageEncodedForUser, json)
            }
        }
    }
}