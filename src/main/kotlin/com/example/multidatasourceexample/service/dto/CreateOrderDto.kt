package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem

data class CreateOrderDto(
    val memberId: Long,
    val createOrderItemsDto: CreateOrderItemsDto,
) {
    fun toEntity(): Order = with(receiver = this) {
        Order.create(memberId = memberId, orderItems = createOrderItemsDto.items.map { it.toEntity() })
    }
}

data class CreateOrderItemsDto(
    val items: List<CreateOrderItemDto>,
)

data class CreateOrderItemDto(
    val category: String,
    val itemName: String,
    val purchasePrice: Float,
) {
    fun toEntity(): OrderItem = with(receiver = this) {
        OrderItem.create(category = category, itemName = itemName, purchasePrice = purchasePrice)
    }
}

data class CreateOrderResultDto(
    val orderId: Long,
) {
    companion object {
        operator fun invoke(order: Order) = CreateOrderResultDto(orderId = order.id)
    }
}
