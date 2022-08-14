package com.example.multidatasourceexample.service.order

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import com.example.multidatasourceexample.domain.order.repository.OrderRepository
import com.example.multidatasourceexample.service.dto.CreateOrderDto
import com.example.multidatasourceexample.service.dto.CreateOrderResultDto
import com.example.multidatasourceexample.service.dto.event.CreatedOrderEventDto
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
@Transactional(transactionManager = "orderTransactionManager")
class OrderService(
    private val orderRepository: OrderRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
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
        applicationEventPublisher.publishEvent(CreatedOrderEventDto(orderId = order.id))

        logger.info("[ OUT ] <--- createOrder()")

        return CreateOrderResultDto.of(order = order)
    }

    fun changeCompleteStatus(id: Long) {
        val order: Order = findOrder(id = id)
        order.changeStatus(status = OrderStatus.COMPLETE)
    }

    fun changeFailStatus(id: Long) {
        val order: Order = findOrder(id = id)
        order.changeStatus(status = OrderStatus.FAIL)
    }

    private fun findOrder(id: Long): Order =
        orderRepository.findByIdOrNull(id = id) ?: throw NoSuchElementException(ExceptionMessage.ORDER_NOT_FOUND)
}
