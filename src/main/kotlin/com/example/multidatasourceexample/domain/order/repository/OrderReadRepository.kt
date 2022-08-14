package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.order.entity.Order

interface OrderReadRepository {
    fun findAllByLimitAndOffset(limit: Int, offset: Int): Pair<List<Order>, Long>
    fun findById(id: Long): Order?
    fun findByIdWithOrderItem(id: Long): Order?
}
