package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import java.time.LocalDateTime

data class FindOrderResultDto(
    val orderId: Long,
    val memberId: Long,
    val statusLabel: String,
    val orderedAt: LocalDateTime,
    val orderItems: List<FindOrderItemResultDto>,
) {
    companion object {
        fun of(order: Order, isEmptyOrderItems: Boolean = true) = FindOrderResultDto(
            orderId = order.id,
            memberId = order.memberId,
            statusLabel = order.status.label,
            orderedAt = order.orderedAt,
            orderItems = getFindOrderItemRessultDtos(
                orderItems = order.orderItems,
                isEmptyOrderItems = isEmptyOrderItems,
            ),
        )

        private fun getFindOrderItemRessultDtos(orderItems: List<OrderItem>, isEmptyOrderItems: Boolean) =
            if (isEmptyOrderItems) listOf() else orderItems.map { FindOrderItemResultDto.of(orderItem = it) }
    }
}
