package com.example.multidatasourceexample.domain.pay.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class PayTests : DescribeSpec(
    {
        describe("changeStatus 메서드는") {
            it("결제 상태를 변경한다.") {
                // given
                val pay = Pay.create(id = 1L, orderId = 1L)

                // when
                pay.changeStatus(value = "cancel")

                // then
                pay.status.shouldBe(PayStatus.CANCEL)
            }

            context("결제 취소 상태인 경우") {
                it("결제 상태를 변경할 수 없으며, IllegalStateException 예외를 던진다.") {
                    // given
                    val pay = Pay.create(id = 1L, orderId = 1L)
                    pay.changeStatus(value = "cancel")

                    // when
                    val exception = shouldThrow<IllegalStateException> { pay.changeStatus(value = "complete") }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.PAY_STATUS_IS_CANCEL)
                }
            }

            context("결제 실패 상태인 경우") {
                it("결제 상태를 변경할 수 없으며, IllegalStateException 예외를 던진다.") {
                    // given
                    val pay = Pay.create(id = 1L, orderId = 1L)
                    pay.changeStatus(value = "fail")

                    // when
                    val exception = shouldThrow<IllegalStateException> { pay.changeStatus(value = "complete") }

                    // then
                    exception.localizedMessage.shouldBe(ExceptionMessage.PAY_STATUS_IS_FAIL)
                }
            }
        }
    },
)
