package com.example.multidatasourceexample.service

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import com.example.multidatasourceexample.domain.order.entity.OrderStatus
import com.example.multidatasourceexample.domain.order.repository.OrderReadRepository
import com.example.multidatasourceexample.service.dto.FindOrderResultDto
import com.example.multidatasourceexample.service.order.OrderReadService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class OrderReadServiceTests : DescribeSpec(
    {
        val mockOrderReadRepository = mockk<OrderReadRepository>()
        val orderReadService = OrderReadService(orderReadRepository = mockOrderReadRepository)

        describe("findOrders 메서드는") {
            it("Order 엔티티 리스트 결과를 Dto 리스트로 변환해서 반환한다.") {
                // given
                val pageNumber = 1
                val pageSize = 3
                val offset = pageNumber.minus(1)
                val total = 3L
                val orderPair by lazy {
                    Pair(
                        first = (1L..3L).map {
                            val orderItems = listOf(
                                OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                                OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                            )
                            Order.create(memberId = it, orderItems = orderItems)
                        },
                        second = total,
                    )
                }

                every {
                    mockOrderReadRepository.findAllByLimitAndOffset(
                        limit = pageSize,
                        offset = offset,
                    )
                } returns orderPair

                // when
                withContext(Dispatchers.IO) {
                    orderReadService.findOrders(pageNumber = pageNumber, pageSize = pageSize)
                }

                // then
                verify { mockOrderReadRepository.findAllByLimitAndOffset(limit = pageSize, offset = offset) }
            }
        }

        describe("findOrdersByStatus 메서드는") {
            it("Order 상태별 엔티티 리스트 결과를 Dto 리스트로 변환해서 반환한다.") {
                // given
                val status = "wait"
                val pageNumber = 1
                val pageSize = 3
                val offset = pageNumber.minus(1)
                val total = 3L
                val orderPair by lazy {
                    Pair(
                        first = (1L..3L).map {
                            val orderItems = listOf(
                                OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                                OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                            )
                            Order.create(memberId = it, orderItems = orderItems)
                        },
                        second = total,
                    )
                }

                every {
                    mockOrderReadRepository.findAllByStatusAndLimitAndOffset(
                        status = OrderStatus.convert(value = status),
                        limit = pageSize,
                        offset = offset,
                    )
                } returns orderPair

                // when
                withContext(Dispatchers.IO) {
                    orderReadService.findOrdersByStatus(status = status, pageNumber = pageNumber, pageSize = pageSize)
                }

                // then
                verify {
                    mockOrderReadRepository.findAllByStatusAndLimitAndOffset(
                        status = OrderStatus.convert(value = status),
                        limit = pageSize,
                        offset = offset,
                    )
                }
            }
        }

        describe("findOrderWithOrderItem 메서드는") {
            it("Order 및 OrderItem 엔티티 결과를 Dto 객체로 변환해서 반환한다.") {
                // given
                val orderId = 1L
                val order by lazy {
                    val memberId = 1L
                    val orderItems = listOf(
                        OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                        OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                    )
                    Order.create(memberId = memberId, orderItems = orderItems)
                }
                val excpectFindOrderResultdto by lazy { FindOrderResultDto.of(order = order) }

                every { mockOrderReadRepository.findByIdWithOrderItem(id = orderId) } returns order

                // when
                val findOrderResultdto = withContext(Dispatchers.IO) {
                    orderReadService.findOrderWithOrderItem(id = orderId)
                }

                // then
                verify { mockOrderReadRepository.findByIdWithOrderItem(id = orderId) }

                findOrderResultdto.shouldBe(excpectFindOrderResultdto)
            }
        }
    },
)
