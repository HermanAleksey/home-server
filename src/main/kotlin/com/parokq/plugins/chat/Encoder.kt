package com.parokq.plugins.chat

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

private const val RSA_ALGORITHM = "RSA/ECB/PKCS1Padding"

class Encoder {

    private val cipher: Cipher = Cipher.getInstance(RSA_ALGORITHM)

    fun encryptMessage(message: ByteArray, publicKey: PublicKey): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(message)
    }

    fun decryptMessage(message: ByteArray, privateKey: PrivateKey): ByteArray {
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(message)
    }

    fun encryptPublicKey(key: PublicKey): String {
        val keyBytes: ByteArray = key.encoded
        return Base64.getEncoder().encodeToString(keyBytes)
    }

    fun decryptPublicKey(keyString: String): PublicKey {
        val keyBytes: ByteArray = Base64.getDecoder().decode(keyString)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        // Указываем алгоритм, который использовался для генерации ключа
        return keyFactory.generatePublic(keySpec)
    }
}

fun main() {
    val encoder = Encoder()
    val pairGenerator = KeyFactory()

    val (publicKey, privateKey) = pairGenerator.generateKeyPair()
    println("Generated:")
    println("$publicKey")

    val encodedPublicKey = encoder.encryptPublicKey(publicKey)
    println("\n\nEncoded:")
    println(encodedPublicKey)

    val decodedPublicKey = encoder.decryptPublicKey(encodedPublicKey)
    println("\n\nDecoded:")
    println(decodedPublicKey)

    println("\n\n\n\n test")
    val string = "Hello!".toByteArray()
    val encodedString = encoder.encryptMessage(string, publicKey)
    val decodedString = encoder.decryptMessage(encodedString, privateKey)
    println("encodedString:\n$encodedString")
    println("decodedString:\n$decodedString")
    println("decodedString:\n${decodedString.toString()}")
    println("decodedString:\n${String(decodedString)}")
}