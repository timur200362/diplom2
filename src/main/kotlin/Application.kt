package com.example

import com.example.data.Location
import com.example.data.SightEntity
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    initDatabase()
    addSight(SightEntity(
        0,
        "ул. Территория Кремля, 13",
        Location(55.7983, 49.1052),
        "Мечеть Кул-Шариф",
        ""
    ))
    configureSerialization()
    configureRouting()
}
