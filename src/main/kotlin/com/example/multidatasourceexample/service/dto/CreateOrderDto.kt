package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.domain.order.entity.Order

data class CreateOrderDto(
    val memberId: Long,
    val createOrderItemsDto: CreateOrderItemsDto,
)

data class CreateOrderItemsDto(
    val items: List<CreateOrderItemDto>,
)

data class CreateOrderItemDto(
    val category: String,
    val itemName: String,
    val purchasePrice: Float,
)

data class CreateOrderResultDto(
    val orderId: Long,
) {
    companion object {
        fun of(order: Order) = CreateOrderResultDto(orderId = order.id)
    }
}
