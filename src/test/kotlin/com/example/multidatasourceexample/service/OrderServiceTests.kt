package com.example.multidatasourceexample.service

import com.example.multidatasourceexample.domain.order.repository.OrderRepository
import com.example.multidatasourceexample.service.dto.CreateOrderDto
import com.example.multidatasourceexample.service.dto.CreateOrderItemDto
import com.example.multidatasourceexample.service.dto.CreateOrderItemsDto
import com.example.multidatasourceexample.service.order.OrderService
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.ApplicationEventPublisher

internal class OrderServiceTests : DescribeSpec(
    {
        val mockOrderRepository = mockk<OrderRepository>()
        val mockApplicationEventPublisher = mockk<ApplicationEventPublisher>(relaxed = true)
        val orderService = OrderService(
            orderRepository = mockOrderRepository,
            applicationEventPublisher = mockApplicationEventPublisher,
        )

        describe("createOrder 메서드는") {
            // given
            val dto = CreateOrderDto(
                memberId = 1L,
                createOrderItemsDto = CreateOrderItemsDto(
                    items = listOf(
                        CreateOrderItemDto(
                            category = "book",
                            itemName = "Spring Guide 1.0",
                            purchasePrice = 23_000f,
                        ),
                        CreateOrderItemDto(
                            category = "book",
                            itemName = "Jpa Core",
                            purchasePrice = 53_000f,
                        ),
                    ),
                ),
            )

            every { mockOrderRepository.save(any()) } answers { firstArg() }
            justRun { mockApplicationEventPublisher.publishEvent(any()) }

            // when
            withContext(Dispatchers.IO) { orderService.createOrder(dto = dto) }

            // then
            verify { mockOrderRepository.save(any()) }
        }
    },
)
