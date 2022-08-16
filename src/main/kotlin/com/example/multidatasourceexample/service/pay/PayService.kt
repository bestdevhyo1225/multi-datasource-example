package com.example.multidatasourceexample.service.pay

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import com.example.multidatasourceexample.domain.pay.entity.Pay
import com.example.multidatasourceexample.domain.pay.entity.PayStatus
import com.example.multidatasourceexample.domain.pay.repository.PayRepository
import com.example.multidatasourceexample.service.dto.event.CreateFailedPayEventDto
import com.example.multidatasourceexample.service.dto.event.CreatedPayEventDto
import com.example.multidatasourceexample.service.dto.event.FailurePaymentEventDto
import com.example.multidatasourceexample.service.dto.event.SuccessPaymentEventDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
@Transactional(transactionManager = "payTransactionManager")
class PayService(
    private val payRepository: PayRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val payExternalApiCallService: PayExternalApiCallService,
) {

    fun createPay(orderId: Long) {
//        executeOrderFailProcessOnRollback(orderId = orderId)
        applicationEventPublisher.publishEvent(CreateFailedPayEventDto(orderId = orderId))

        val pay: Pay = payRepository.save(Pay.create(orderId = orderId))

        applicationEventPublisher.publishEvent(CreatedPayEventDto(payId = pay.id, orderId = orderId))
    }

    fun processPayment(id: Long, orderId: Long) {
        runCatching {
            payExternalApiCallService.execute()
        }.getOrElse {
            val pay: Pay = findPay(id = id)
            pay.changeStatus(status = PayStatus.FAIL)
            applicationEventPublisher.publishEvent(FailurePaymentEventDto(orderId = orderId))
            throw RuntimeException(ExceptionMessage.PAYMENT_REQUEST_FAILURE)
        }

        val pay: Pay = findPay(id = id)
        pay.changeStatus(status = PayStatus.COMPLETE)
        applicationEventPublisher.publishEvent(SuccessPaymentEventDto(orderId = orderId))
    }

    private fun executeOrderFailProcessOnRollback(orderId: Long) {
        TransactionSynchronizationManager.registerSynchronization(
            object : TransactionSynchronization {
                override fun afterCompletion(status: Int) {
                    if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                        applicationEventPublisher.publishEvent(FailurePaymentEventDto(orderId = orderId))
                    }
                }
            },
        )
    }

    private fun findPay(id: Long): Pay =
        payRepository.findByIdOrNull(id = id) ?: throw NoSuchElementException(ExceptionMessage.PAY_NOT_FOUND)
}
