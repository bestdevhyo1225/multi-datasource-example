package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.common.utils.DateTimeFormatterUtils
import com.example.multidatasourceexample.domain.order.entity.OrderItem

data class FindOrderItemResultDto(
    val orderItemId: Long,
    val categoryLabel: String,
    val itemName: String,
    val purchasePrice: String,
    val createdAt: String,
) {
    companion object {
        fun of(orderItem: OrderItem) = FindOrderItemResultDto(
            orderItemId = orderItem.id,
            categoryLabel = orderItem.category.label,
            itemName = orderItem.itemName,
            purchasePrice = orderItem.purchasePrice.toString(),
            createdAt = DateTimeFormatterUtils.toStringDateTime(localDateTime = orderItem.createdAt),
        )
    }
}
