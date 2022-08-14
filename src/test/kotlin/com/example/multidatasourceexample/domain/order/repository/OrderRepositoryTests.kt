package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.domain.order.entity.Order
import com.example.multidatasourceexample.domain.order.entity.OrderItem
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

internal class OrderRepositoryTests : OrderBaseRepositoryTestable, DescribeSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var orderRepository: OrderRepository

    init {
        this.describe("save 메서드는") {
            it("Order 엔티티를 저장한다.") {
                // given
                val memberId = 1L
                val orderItems = listOf(
                    OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                    OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                )
                val order = Order.create(memberId = memberId, orderItems = orderItems)

                // when
                withContext(Dispatchers.IO) { orderRepository.save(order) }

                // then
                val findOrder = orderRepository.findByIdOrNull(id = order.id)!!

                findOrder.shouldBe(order)
                findOrder.orderItems.shouldHaveSize(orderItems.size)
            }
        }
    }
}
