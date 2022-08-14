package com.example.multidatasourceexample.service.order

import com.example.multidatasourceexample.config.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import com.example.multidatasourceexample.domain.order.repository.OrderReadRepository
import com.example.multidatasourceexample.service.dto.FindListResultDto
import com.example.multidatasourceexample.service.dto.FindOrderResultDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
@Transactional(transactionManager = "orderTransactionManager", readOnly = true)
class OrderReadService(
    private val orderReadRepository: OrderReadRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun findOrders(pageNumber: Int, pageSize: Int): FindListResultDto<FindOrderResultDto> {
        logger.info(
            "[ IN ] ---> findOrders(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val findOrderResultDto: FindListResultDto<FindOrderResultDto> = orderReadRepository
            .findAllByLimitAndOffset(limit = pageSize, offset = pageNumber.minus(1).times(pageSize))
            .let { result: Pair<List<Order>, Long> ->
                FindListResultDto(
                    items = result.first.map { order: Order -> FindOrderResultDto.of(order = order) },
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    total = result.second,
                )
            }

        logger.info("[ OUT ] <--- findOrders()")

        return findOrderResultDto
    }

    fun findOrdersByStatus(status: String, pageNumber: Int, pageSize: Int): FindListResultDto<FindOrderResultDto> {
        logger.info(
            "[ IN ] ---> findOrdersByStatus(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val findOrderResultDto: FindListResultDto<FindOrderResultDto> = orderReadRepository
            .findAllByStatusAndLimitAndOffset(
                status = OrderStatus.convert(value = status),
                limit = pageSize,
                offset = pageNumber.minus(1).times(pageSize),
            )
            .let { result: Pair<List<Order>, Long> ->
                FindListResultDto(
                    items = result.first.map { order: Order ->
                        FindOrderResultDto.of(
                            order = order,
                            isLazyLoading = true,
                        )
                    },
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    total = result.second,
                )
            }

        logger.info("[ OUT ] <--- findOrdersByStatus()")

        return findOrderResultDto
    }

    fun findOrderWithOrderItem(id: Long): FindOrderResultDto {
        logger.info(
            "[ IN ] ---> findOrderWithOrderItem(), isReadOnly: {}",
            TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
        )

        val order: Order = orderReadRepository.findByIdWithOrderItem(id = id)
            ?: throw NoSuchElementException(ExceptionMessage.ORDER_NOT_FOUND)

        logger.info("[ OUT ] <--- findOrderWithOrderItem()")

        return order.let { FindOrderResultDto.of(order = it, isLazyLoading = true) }
    }
}
