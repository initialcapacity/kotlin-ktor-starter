package test.collective.start

import com.google.api.client.json.webtoken.JsonWebSignature
import com.google.auth.oauth2.TokenVerifier
import io.collective.start.moduleWithDependencies
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
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
        handleRequest(HttpMethod.Get, "/authenticated") {
            addHeader("X-Goog-IAP-JWT-Assertion", "aToken")
        }.apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("Authenticated users with basic access."))
        }
    }

    @Test
    fun testAuthnException() = exceptionApp {
        handleRequest(HttpMethod.Get, "/authenticated") {
            addHeader("X-Goog-IAP-JWT-Assertion", "aToken")
        }.apply {
            assertEquals(200, response.status()?.value)
            println(response.content!!)
            assertTrue(response.content!!.contains("Sorry, your email account is not authorized. ouch!"))
        }
    }

    @Test
    fun testAuthz() = testApp {
        handleRequest(HttpMethod.Get, "/authorized") {
            addHeader("X-Goog-IAP-JWT-Assertion", "aToken")
        }.apply {
            assertEquals(200, response.status()?.value)
            assertTrue(response.content!!.contains("Authenticated users with special access."))
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        val verifier = mockk<TokenVerifier>()
        val token = mockk<JsonWebSignature>()
        every { verifier.verify("aToken") } returns token

        withTestApplication({ moduleWithDependencies(verifier) }) { callback() }
    }

    private fun exceptionApp(callback: TestApplicationEngine.() -> Unit) {
        val verifier = mockk<TokenVerifier>()
        every { verifier.verify("aToken") } throws Exception("ouch!")

        withTestApplication({ moduleWithDependencies(verifier) }) { callback() }
    }
}