package com.example.data

import kotlinx.serialization.Serializable

@Serializable
data class SightEntity(
    val id: Int,
    val address: String,
    val location: Location,
    val name: String,
    val description: String
)
