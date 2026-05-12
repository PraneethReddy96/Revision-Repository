package com.example.recyclerview.data.model

import kotlinx.serialization.Serializable

/**
 * SENIOR DEV CONCEPT: Data Models
 * We use data classes to represent our domain/data layer entities.
 * In a real app, you might have separate models for Network (DTOs) and Domain.
 */
@Serializable
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)
