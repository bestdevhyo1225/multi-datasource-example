package com.example.multidatasourceexample.service.dto

data class CreateOrderItemsDto(
    val items: List<CreateOrderItemDto>,
)

data class CreateOrderItemDto(
    val category: String,
    val itemName: String,
    val purchasePrice: Float,
)
