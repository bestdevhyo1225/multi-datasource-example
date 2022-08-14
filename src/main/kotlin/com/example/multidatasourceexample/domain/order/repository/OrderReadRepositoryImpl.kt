package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.AbstractReadRepositoryImpl
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.QOrder.order
import com.example.multidatasourceexample.domain.order.entity.QOrderItem.orderItem
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(transactionManager = "orderTransactionManager", readOnly = true)
class OrderReadRepositoryImpl(
    @Qualifier(value = "orderJpaQueryFactory")
    private val orderJpaQueryFactory: JPAQueryFactory,
    private val orderRepository: OrderRepository,
) : AbstractReadRepositoryImpl(), OrderReadRepository {

    override fun findAllByLimitAndOffset(limit: Int, offset: Int): Pair<List<Order>, Long> = runBlocking {
        val orders: Deferred<List<Order>> = async(context = Dispatchers.IO) {
            orderJpaQueryFactory
                .selectFrom(order)
                .limit(getLimit(limit = limit))
                .offset(getOffset(offset = offset))
                .fetch()
        }

        val orderCounts: Deferred<Long> = async(context = Dispatchers.IO) {
            orderRepository.countOrders()
        }

        Pair(first = orders.await(), second = orderCounts.await())
    }

    override fun findById(id: Long): Order? =
        orderJpaQueryFactory
            .selectFrom(order)
            .where(orderIdEq(id = id))
            .fetchOne()

    override fun findByIdWithOrderItem(id: Long): Order? =
        orderJpaQueryFactory
            .selectFrom(order)
            .innerJoin(order.orderItems, orderItem).fetchJoin()
            .where(orderIdEq(id = id))
            .fetchOne()

    private fun orderIdEq(id: Long): BooleanExpression = order.id.eq(id)
}
