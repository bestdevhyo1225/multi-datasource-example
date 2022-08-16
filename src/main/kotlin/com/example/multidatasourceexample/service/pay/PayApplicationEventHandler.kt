package com.example.multidatasourceexample.service.pay

import com.example.multidatasourceexample.service.dto.event.CreatedPayEventDto
import com.example.multidatasourceexample.service.dto.event.FailurePaymentEventDto
import com.example.multidatasourceexample.service.dto.event.SuccessPaymentEventDto
import com.example.multidatasourceexample.service.order.OrderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PayApplicationEventHandler(
    private val payService: PayService,
    private val orderService: OrderService,
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(eventDto: CreatedPayEventDto) = runBlocking {
        launch(context = Dispatchers.IO) {
            payService.processPayment(id = eventDto.payId, orderId = eventDto.orderId)
        }
    }

    @EventListener
    fun handle(eventDto: SuccessPaymentEventDto) = runBlocking {
        launch(context = Dispatchers.IO) {
            orderService.changeCompleteStatus(id = eventDto.orderId)
        }
    }

    @EventListener
    fun handle(eventDto: FailurePaymentEventDto) = runBlocking {
        launch(context = Dispatchers.IO) {
            orderService.changeFailStatus(id = eventDto.orderId)
        }
    }
}
