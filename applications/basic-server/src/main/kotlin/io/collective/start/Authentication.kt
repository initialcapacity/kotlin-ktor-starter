package io.collective.start

import com.google.api.client.json.webtoken.JsonWebSignature
import com.google.auth.oauth2.TokenVerifier
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authenticated(verifier: TokenVerifier, callback: Route.() -> Unit): Route {
    val routeWithAuthentication = this.createChild(object : RouteSelector(1.0) {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
            RouteSelectorEvaluation.Constant
    })
    routeWithAuthentication.intercept(ApplicationCallPipeline.Features) {
        val assertion = call.request.headers.get("X-Goog-IAP-JWT-Assertion")
        var exception = "Sorry, your email account is not authorized. "
        var token:JsonWebSignature? = null
        try {
            token = verifier.verify(assertion)
        } catch (e: Exception) {
            exception += e.localizedMessage
        }

        if (assertion == null || token == null) {
            call.respond(
                FreeMarkerContent(
                    "index.ftl",
                    mapOf("message" to exception)
                )
            )
            finish()
        }
    }
    callback(routeWithAuthentication)
    return routeWithAuthentication
}