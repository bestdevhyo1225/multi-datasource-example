package com.example.multidatasourceexample.domain.order.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull

internal class OrderItemTests : DescribeSpec(
    {
        describe("create 메서드는") {
            it("OrderItem 엔티티를 생성한다.") {
                // given
                val category = "book"
                val itemName = "Spring Guide 1.0"
                val purchasePrice = 35_000f

                // when
                val orderItem =
                    OrderItem.create(category = category, itemName = itemName, purchasePrice = purchasePrice)

                // then
                orderItem.shouldNotBeNull()
            }

            it("Id를 포함한 OrderItem 엔티티를 생성한다.") {
                // given
                val orderItemId = 1L
                val category = "book"
                val itemName = "Spring Guide 1.0"
                val purchasePrice = 35_000f

                // when
                val orderItem = OrderItem.create(
                    id = orderItemId,
                    category = category,
                    itemName = itemName,
                    purchasePrice = purchasePrice,
                )

                // then
                orderItem.shouldNotBeNull()
            }
        }
    },
)
