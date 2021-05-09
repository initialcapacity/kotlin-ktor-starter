package test.collective.start

import io.collective.start.module
import io.ktor.http.HttpMethod
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class AppTest {

    @Test
    fun testEmptyHome() = testApp {
        handleRequest(HttpMethod.Get, "/").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("An example application using Kotlin and Ktor"))
        }
    }

    @Test
    fun testAuthn() = testApp {
        handleRequest(HttpMethod.Get, "/authenticated").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("Authenticated users with basic access."))
        }
    }

    @Test
    fun testAuthz() = testApp {
        handleRequest(HttpMethod.Get, "/authorized").apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("Authenticated users with special access."))
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ module() }) { callback() }
    }
}