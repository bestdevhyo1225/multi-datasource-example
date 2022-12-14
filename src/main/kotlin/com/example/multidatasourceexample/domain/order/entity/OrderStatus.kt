package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.Cancelable
import com.example.multidatasourceexample.domain.Failable

enum class OrderStatus(val label: String) : Cancelable, Failable {
    WAIT("주문 대기") {
        override fun isCanceled() = false
        override fun isFailed() = false
    },
    COMPLETE("주문 성공") {
        override fun isCanceled() = false
        override fun isFailed() = false
    },
    CANCEL("주문 취소") {
        override fun isCanceled() = true
        override fun isFailed() = false
    },
    FAIL("주문 실패") {
        override fun isCanceled() = false
        override fun isFailed() = true
    };

    companion object {
        fun convert(value: String): OrderStatus {
            return try {
                OrderStatus.valueOf(value.uppercase())
            } catch (exception: Exception) {
                throw IllegalArgumentException(ExceptionMessage.ORDER_STATUS_NOT_EXISTS)
            }
        }
    }
}
