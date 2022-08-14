package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class OrderItemCategoryTests : DescribeSpec(
    {
        describe("convert 메서드는") {
            it("String 타입을 OrderItemCategory 타입으로 변경한다.") {
                // given
                val value = "book"

                // when
                val orderItemCategory = OrderItemCategory.convert(value = value)

                // then
                orderItemCategory.shouldBe(OrderItemCategory.BOOK)
            }

            context("존재하지 않는 주문 카테고리로 변환하려고 하는 경우") {
                it("IllegalArgumentException 예외를 던진다.") {
                    // given
                    val value = "bookxzdf"

                    // when
                    val exception = shouldThrow<IllegalArgumentException> { OrderItemCategory.convert(value = value) }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.ORDER_ITEM_CATEGORY_NOT_EXISTS)
                }
            }
        }
    },
)
