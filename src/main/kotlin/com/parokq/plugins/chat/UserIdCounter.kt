package com.parokq.plugins.chat

import java.util.concurrent.atomic.AtomicInteger

object UserIdCounter {

    private val lastId = AtomicInteger(0)

    fun generateUserId(): Int {
        return lastId.incrementAndGet()
    }
}