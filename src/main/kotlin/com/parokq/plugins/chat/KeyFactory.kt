package com.parokq.plugins.chat

import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

private const val ALGORITHM_NAME = "RSA"
private const val RSA_KEY_SIZE = 1024 // размер ключа

class KeyFactory {

    fun generateKeyPair(): Pair<PublicKey, PrivateKey> {
        val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME)
        keyPairGenerator.initialize(RSA_KEY_SIZE)
        val keyPair = keyPairGenerator.generateKeyPair()
        val mPublicKey = keyPair.public ?: throw IllegalStateException("Key was not generated")
        val mPrivateKey = keyPair.private ?: throw IllegalStateException("Key was not generated")
        return mPublicKey to mPrivateKey
    }
}