package com.tictactoe

import com.tictactoe.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
