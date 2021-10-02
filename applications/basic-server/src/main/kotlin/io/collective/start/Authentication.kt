package io.collective.start

import com.google.auth.oauth2.TokenVerifier
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authenticated(verifier: TokenVerifier, callback: Route.() -> Unit): Route {
    val routeWithAuthentication = this.createChild(object : RouteSelector() {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
            RouteSelectorEvaluation.Constant
    })
    routeWithAuthentication.intercept(ApplicationCallPipeline.Features) {
        val assertion = call.request.headers.get("X-Goog-IAP-JWT-Assertion")

        if (assertion == null) {
            val message = "Sorry, your email account is not authorized. missing json assertion"
            call.respond(FreeMarkerContent("index.ftl", mapOf("message" to message)))
            finish()
            return@intercept
        }

        try {
            verifier.verify(assertion)
        } catch (e: Exception) {
            val message = "Sorry, your email account is not authorized."
            call.respond(FreeMarkerContent("index.ftl", mapOf("message" to "$message ${e.message}")))
            finish()
        }
    }
    callback(routeWithAuthentication)
    return routeWithAuthentication
}