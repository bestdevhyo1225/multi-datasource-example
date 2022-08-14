package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderStatus

interface OrderReadRepository {
    fun findAllByLimitAndOffset(limit: Int, offset: Int): Pair<List<Order>, Long>
    fun findAllByStatusAndLimitAndOffset(status: OrderStatus, limit: Int, offset: Int): Pair<List<Order>, Long>
    fun findById(id: Long): Order?
    fun findByIdWithOrderItem(id: Long): Order?
}
