package com.example.multidatasourceexample.service.order

import com.example.multidatasourceexample.config.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import com.example.multidatasourceexample.domain.order.repository.OrderReadRepository
import com.example.multidatasourceexample.service.dto.FindListResultDto
import com.example.multidatasourceexample.service.dto.FindOrderResultDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(transactionManager = "orderTransactionManager", readOnly = true)
class OrderReadService(
    private val orderReadRepository: OrderReadRepository,
) {

    fun findOrders(pageNumber: Int, pageSize: Int): FindListResultDto<FindOrderResultDto> =
        orderReadRepository
            .findAllByLimitAndOffset(limit = pageSize, offset = pageNumber.minus(1).times(pageSize))
            .let { result: Pair<List<Order>, Long> ->
                FindListResultDto(
                    items = result.first.map { order: Order -> FindOrderResultDto.of(order = order) },
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    total = result.second,
                )
            }

    fun findOrdersByStatus(status: String, pageNumber: Int, pageSize: Int): FindListResultDto<FindOrderResultDto> =
        orderReadRepository
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
                            isEmptyOrderItems = false,
                        )
                    },
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    total = result.second,
                )
            }

    fun findOrderWithOrderItem(id: Long): FindOrderResultDto {
        val order: Order = orderReadRepository.findByIdWithOrderItem(id = id)
            ?: throw NoSuchElementException(ExceptionMessage.ORDER_NOT_FOUND)

        return order.let { FindOrderResultDto.of(order = it) }
    }
}
