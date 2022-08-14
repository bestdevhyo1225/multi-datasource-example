package com.example.multidatasourceexample.domain.pay.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.Cancelable
import com.example.multidatasourceexample.domain.Failable

enum class PayStatus(val label: String) : Cancelable, Failable {
    WAIT("결제 대기") {
        override fun isCanceled() = false
        override fun isFailed() = false
    },
    COMPLETE("결제 완료") {
        override fun isCanceled() = false
        override fun isFailed() = false
    },
    CANCEL("결제 취소") {
        override fun isCanceled() = true
        override fun isFailed() = false
    },
    FAIL("결제 실패") {
        override fun isCanceled() = false
        override fun isFailed() = true
    };

    companion object {
        fun convert(value: String): PayStatus =
            try {
                PayStatus.valueOf(value.uppercase())
            } catch (exception: Exception) {
                throw IllegalArgumentException(ExceptionMessage.PAY_STATUS_NOT_EXISTS)
            }
    }
}
