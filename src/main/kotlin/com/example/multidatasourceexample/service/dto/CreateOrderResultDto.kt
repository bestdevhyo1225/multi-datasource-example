package com.example.multidatasourceexample.service.dto

import com.example.multidatasourceexample.domain.order.entity.Order

data class CreateOrderResultDto(
    val orderId: Long,
) {
    companion object {
        fun of(order: Order) = CreateOrderResultDto(orderId = order.id)
    }
}
