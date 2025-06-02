package com.example

import com.example.data.Location
import com.example.data.SightEntity
import com.example.data.Sights
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        get("/sights") {
            call.respond(getSights())
        }
    }
}

fun initDatabase() {
    Database.connect("jdbc:sqlite:sights.db", driver = "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(Sights) // Создание таблицы, если она не существует
    }
}

fun addSight(sight: SightEntity) {
    transaction {
        Sights.insert {
            it[address] = sight.address
            it[latitude] = sight.location.latitude
            it[longitude] = sight.location.longitude
            it[name] = sight.name
            it[description] = sight.description
        }
    }
}

fun getSights(): List<SightEntity> {
    return transaction {
        Sights.selectAll().map {
            SightEntity(
                id = it[Sights.id].value,
                address = it[Sights.address],
                location = Location(it[Sights.latitude], it[Sights.longitude]),
                name = it[Sights.name],
                description = it[Sights.description]
            )
        }
    }
}

fun deleteSight(id: Int) {
    transaction {
        Sights.deleteWhere { Sights.id eq id }
    }
}


