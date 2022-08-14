package com.example.multidatasourceexample.service.order

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import com.example.multidatasourceexample.domain.order.repository.OrderRepository
import com.example.multidatasourceexample.service.dto.CreateOrderDto
import com.example.multidatasourceexample.service.dto.CreateOrderResultDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
@Transactional(transactionManager = "orderTransactionManager")
class OrderService(
    private val orderRepository: OrderRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createOrder(dto: CreateOrderDto): CreateOrderResultDto {
        logger.info(
            "[ IN ] ---> createOrder(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val order: Order = Order.create(
            memberId = dto.memberId,
            orderItems = dto.createOrderItemsDto.items.map {
                OrderItem.create(category = it.category, itemName = it.itemName, purchasePrice = it.purchasePrice)
            },
        )

        orderRepository.save(order)

        logger.info("[ OUT ] <--- createOrder()")

        return CreateOrderResultDto.of(order = order)
    }
}
