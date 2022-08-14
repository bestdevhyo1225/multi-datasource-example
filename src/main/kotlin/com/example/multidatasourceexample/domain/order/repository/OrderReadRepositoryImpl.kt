package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.AbstractReadRepositoryImpl
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import com.example.multidatasourceexample.domain.order.entity.QOrder.order
import com.example.multidatasourceexample.domain.order.entity.QOrderItem.orderItem
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Repository
@Transactional(transactionManager = "orderTransactionManager", readOnly = true)
class OrderReadRepositoryImpl(
    @Qualifier(value = "orderJpaQueryFactory")
    private val orderJpaQueryFactory: JPAQueryFactory,
    private val orderRepository: OrderRepository,
) : AbstractReadRepositoryImpl(), OrderReadRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAllByLimitAndOffset(limit: Int, offset: Int): Pair<List<Order>, Long> {
        logger.info(
            "[ IN ] ---> findAllByLimitAndOffset(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val result = Pair(
            first = orderJpaQueryFactory
                .selectFrom(order)
                .limit(getLimit(limit = limit))
                .offset(getOffset(offset = offset))
                .fetch(),
            second = orderRepository.countOrders(),
        )

        logger.info("[ OUT ] <--- findAllByLimitAndOffset()")

        return result
    }

    override fun findAllByStatusAndLimitAndOffset(
        status: OrderStatus,
        limit: Int,
        offset: Int,
    ): Pair<List<Order>, Long> {
        logger.info(
            "[ IN ] ---> findAllByStatusAndLimitAndOffset(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val result = Pair(
            first = orderJpaQueryFactory
                .selectFrom(order)
                .where(orderStatusEq(status = status))
                .limit(getLimit(limit = limit))
                .offset(getOffset(offset = offset))
                .fetch(),
            second = orderRepository.countOrdersByStatus(status = status),
        )

        logger.info("[ OUT ] <--- findAllByStatusAndLimitAndOffset()")

        return result
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
    private fun orderStatusEq(status: OrderStatus): BooleanExpression = order.status.eq(status)
}
