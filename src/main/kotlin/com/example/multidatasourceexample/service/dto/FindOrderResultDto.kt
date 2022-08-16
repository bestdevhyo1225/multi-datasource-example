package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.common.utils.DateTimeFormatterUtils
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem

data class FindOrderResultDto(
    val orderId: Long,
    val memberId: Long,
    val statusLabel: String,
    val orderedAt: String,
    val orderItems: List<FindOrderItemResultDto>,
) {
    companion object {
        operator fun invoke(order: Order, isLazyLoading: Boolean = false) = FindOrderResultDto(
            orderId = order.id,
            memberId = order.memberId,
            statusLabel = order.status.label,
            orderedAt = DateTimeFormatterUtils.toStringDateTime(localDateTime = order.orderedAt),
            orderItems = getFindOrderItemRessultDtos(
                orderItems = order.orderItems,
                isLazyLoading = isLazyLoading,
            ),
        )

        private fun getFindOrderItemRessultDtos(orderItems: List<OrderItem>, isLazyLoading: Boolean) =
            if (isLazyLoading) orderItems.map { FindOrderItemResultDto(orderItem = it) } else listOf()
    }
}

data class FindOrderItemResultDto(
    val orderItemId: Long,
    val categoryLabel: String,
    val itemName: String,
    val purchasePrice: String,
    val createdAt: String,
) {
    companion object {
        operator fun invoke(orderItem: OrderItem) = FindOrderItemResultDto(
            orderItemId = orderItem.id,
            categoryLabel = orderItem.category.label,
            itemName = orderItem.itemName,
            purchasePrice = orderItem.purchasePrice.toString(),
            createdAt = DateTimeFormatterUtils.toStringDateTime(localDateTime = orderItem.createdAt),
        )
    }
}
