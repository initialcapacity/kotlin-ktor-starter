package io.collective.start

import com.google.auth.oauth2.TokenVerifier
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import io.ktor.util.pipeline.*
import java.util.*

fun Application.module() {
    val issuer = System.getenv("JWT_ISSUER")
    val audience = System.getenv("JWT_AUDIENCE")
    val verifier = TokenVerifier.newBuilder()
        .setIssuer(issuer)
        .setAudience(audience)
        .build()
    moduleWithDependencies(verifier)
}

fun Application.moduleWithDependencies(verifier: TokenVerifier) {
    install(DefaultHeaders)
    install(CallLogging)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Routing) {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("headers" to headers())))
        }
        authenticated(verifier) {
            get("/authenticated") {
                call.respond(FreeMarkerContent("authenticated.ftl", mapOf("headers" to headers())))
            }
            get("/authorized") {
                call.respond(FreeMarkerContent("authorized.ftl", mapOf("headers" to headers())))
            }
        }
        static("images") { resources("images") }
        static("style") { resources("style") }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.headers(): MutableMap<String, String> {
    val headers = mutableMapOf<String, String>()
    call.request.headers.entries().forEach { entry ->
        headers.put(entry.key, entry.value.joinToString())
    }
    return headers
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Jetty, port, watchPaths = listOf("basic-server"), module = Application::module).start()
}