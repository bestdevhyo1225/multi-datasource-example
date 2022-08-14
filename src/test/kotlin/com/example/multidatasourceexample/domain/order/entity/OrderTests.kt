package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.config.constants.ExceptionMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

internal class OrderTests : DescribeSpec(
    {
        describe("create 메서드는") {
            it("Order 엔티티를 생성한다.") {
                // given
                val memberId = 1L
                val orderItems = listOf(
                    OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                    OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                )

                // when
                val order = Order.create(memberId = memberId, orderItems = orderItems)

                // then
                order.shouldNotBeNull()
                order.orderItems.forEach { it.shouldNotBeNull() }
            }

            it("Id를 포함해 Order 엔티티를 생성할 수 있다.") {
                // given
                val orderId = 1L
                val memberId = 1L
                val orderItems = listOf(
                    OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                    OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                )

                // when
                val order = Order.create(id = orderId, memberId = memberId, orderItems = orderItems)

                // then
                order.shouldNotBeNull()
                order.id.shouldBe(orderId)
                order.orderItems.forEach { it.shouldNotBeNull() }
            }
        }

        describe("changeStatus 메서드는") {
            it("주문 상태를 변경한다.") {
                // given
                val memberId = 1L
                val orderItems = listOf(
                    OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                    OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                )
                val order = Order.create(memberId = memberId, orderItems = orderItems)

                // when
                order.changeStatus(value = "complete")

                // then
                order.status.shouldBe(OrderStatus.COMPLETE)
            }

            context("현재 Order 엔티티가 주문 '취소' 상태인 경우") {
                it("IllegalStateException 예외를 던진다.") {
                    // given
                    val memberId = 1L
                    val orderItems = listOf(
                        OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                        OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                    )
                    val order = Order.create(memberId = memberId, orderItems = orderItems)
                    order.changeStatus(value = "cancel")

                    // when
                    val exception = shouldThrow<IllegalStateException> { order.changeStatus(value = "complete") }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.ORDER_STATUS_IS_CANCEL)
                }
            }

            context("현재 Order 엔티티가 주문 '실패' 상태인 경우") {
                it("IllegalStateException 예외를 던진다.") {
                    // given
                    val memberId = 1L
                    val orderItems = listOf(
                        OrderItem.create(category = "book", itemName = "Spring", purchasePrice = 55_000f),
                        OrderItem.create(category = "book", itemName = "JPA", purchasePrice = 43_000f),
                    )
                    val order = Order.create(memberId = memberId, orderItems = orderItems)
                    order.changeStatus(value = "fail")

                    // when
                    val exception = shouldThrow<IllegalStateException> { order.changeStatus(value = "complete") }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.ORDER_STATUS_IS_FAIL)
                }
            }
        }
    },
)
