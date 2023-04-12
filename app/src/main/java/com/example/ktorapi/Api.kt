package com.example.ktorapi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8090) {
        routing {
            get("/menu") {
                val menuItems = listOf(
                    MenuItem("Hamburger", 5.99),
                    MenuItem("French fries", 2.99),
                    MenuItem("Soft drink", 1.99)
                )
                call.respond(menuItems)
            }
        }
    }.start(wait = true)
}
