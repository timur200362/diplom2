package com.example.data

import kotlinx.serialization.Serializable

@Serializable
data class SightEntity(
    val name: String,
    val location: Location,
    val address: String,
    val description: String
)
