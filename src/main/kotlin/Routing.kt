package com.example

import com.example.data.Location
import com.example.data.SightEntity
import com.example.data.Sight
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
        SchemaUtils.create(Sight)
    }
}

fun addSight(sight: SightEntity) {
    transaction {
        Sight.insert {
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
        Sight.selectAll().map {
            SightEntity(
                address = it[Sight.address],
                location = Location(it[Sight.latitude], it[Sight.longitude]),
                name = it[Sight.name],
                description = it[Sight.description]
            )
        }
    }
}

fun deleteSight(id: Int) {
    transaction {
        Sight.deleteWhere { Sight.id eq id }
    }
}


