package com.example.recyclerview.data.model

/**
 * SENIOR DEV CONCEPT: Data Models
 * We use data classes to represent our domain/data layer entities.
 * In a real app, you might have separate models for Network (DTOs) and Domain.
 */
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)
