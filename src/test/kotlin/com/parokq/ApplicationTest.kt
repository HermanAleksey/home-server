package com.parokq

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.parokq.plugins.auth.configureAuthenticationRouting

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureAuthenticationRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
