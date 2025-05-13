package com.example.data

import org.jetbrains.exposed.dao.id.IntIdTable

object Sights : IntIdTable() {
    val address = varchar("address", 255)
    val latitude = double("latitude") // Широта
    val longitude = double("longitude") // Долгота
    val name = varchar("name", 255)
    val description = text("description")
}