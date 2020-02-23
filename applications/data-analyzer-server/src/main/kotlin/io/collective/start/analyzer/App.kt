package io.collective.start.analyzer

import io.collective.workflow.WorkScheduler
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import java.util.*

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("hi!", ContentType.Text.Html)
        }
    }

    val scheduler = WorkScheduler<ExampleTask>(ExampleWorkFinder(), mutableListOf(ExampleWorker()), 30)
    scheduler.start()
}

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Jetty, port, watchPaths = listOf("data-analyzer-server"), module = Application::module).start()
}