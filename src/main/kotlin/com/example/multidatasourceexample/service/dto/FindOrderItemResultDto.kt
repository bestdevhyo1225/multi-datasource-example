package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.domain.order.entity.OrderItem
import java.time.LocalDateTime

data class FindOrderItemResultDto(
    val orderItemId: Long,
    val categoryLabel: String,
    val itemName: String,
    val purchasePrice: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(orderItem: OrderItem) = FindOrderItemResultDto(
            orderItemId = orderItem.id,
            categoryLabel = orderItem.category.label,
            itemName = orderItem.itemName,
            purchasePrice = orderItem.purchasePrice.toString(),
            createdAt = orderItem.createdAt,
        )
    }
}
