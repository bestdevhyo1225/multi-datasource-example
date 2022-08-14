package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

internal class OrderReadRepositoryTests : OrderBaseRepositoryTestable, DescribeSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderReadRepository: OrderReadRepository

    private lateinit var savedOrders: List<Order>

    init {
        this.beforeSpec {
            savedOrders = withContext(Dispatchers.IO) {
                orderRepository.saveAll(
                    (1L..10L).map {
                        Order.create(
                            memberId = it,
                            orderItems = listOf(
                                OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                                OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                            ),
                        )
                    },
                )
            }
        }

        this.afterSpec {
            withContext(Dispatchers.IO) { orderRepository.deleteAll() }
        }

        this.describe("findAllByLimitAndOffset 메서드는") {
            it("Order 엔티티 리스트를 조회한다.") {
                // given
                val limit = 5
                val offset = 2

                // when
                val result = orderReadRepository.findAllByLimitAndOffset(limit = limit, offset = offset)

                // then
                result.first.shouldNotBeEmpty()
                result.second.shouldBe(savedOrders.size)
            }
        }

        this.describe("findById 메서드는") {
            it("Order 엔티티를 조회한다.") {
                // given
                val savedOrder = savedOrders.first()

                // when
                val findOrder = orderReadRepository.findById(id = savedOrder.id)!!

                // then
                findOrder.shouldBe(savedOrder)
            }
        }

        this.describe("findByIdWithOrderItem 메서드는") {
            it("Order, OrderItem 엔티티를 조인해서 조회한다.") {
                // given
                val savedOrder = savedOrders.first()
                val savedOrderItems = savedOrder.orderItems.sortedBy { it.id }

                // when
                val findOrder = orderReadRepository.findByIdWithOrderItem(id = savedOrder.id)!!
                val findOrderItems = findOrder.orderItems.sortedBy { it.id }

                // then
                findOrder.shouldBe(savedOrder)
                findOrderItems.forEachIndexed { index, orderItem -> orderItem.shouldBe(savedOrderItems[index]) }
            }
        }
    }
}
