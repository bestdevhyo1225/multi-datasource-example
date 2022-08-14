package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.config.constants.ExceptionMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class OrderStatusTests : DescribeSpec(
    {
        describe("convert 메서드는") {
            it("String 타입을 OrderStatus 타입으로 변경한다.") {
                // given
                val value = "complete"

                // when
                val orderStatus = OrderStatus.convert(value = value)

                // then
                orderStatus.shouldBe(OrderStatus.COMPLETE)
            }

            context("존재하지 않는 주문 상태로 변환하려고 하는 경우") {
                it("IllegalArgumentException 예외를 던진다.") {
                    // given
                    val value = "completexyz"

                    // when
                    val exception = shouldThrow<IllegalArgumentException> { OrderStatus.convert(value = value) }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.ORDER_STATUS_NOT_EXISTS)
                }
            }
        }
    },
)
