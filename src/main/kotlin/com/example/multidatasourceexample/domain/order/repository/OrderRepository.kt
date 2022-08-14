package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(value = "SELECT COUNT(o) FROM Order o")
    fun countOrders(): Long

    @Query(value = "SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    fun countOrdersByStatus(status: OrderStatus): Long
}
