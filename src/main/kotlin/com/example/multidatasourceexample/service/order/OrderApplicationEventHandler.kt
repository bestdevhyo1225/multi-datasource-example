package com.example.multidatasourceexample.service.order

import com.example.multidatasourceexample.service.dto.event.CreatedOrderEventDto
import com.example.multidatasourceexample.service.pay.PayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderApplicationEventHandler(
    private val payService: PayService,
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: CreatedOrderEventDto) = runBlocking {
        launch(context = Dispatchers.IO) {
            payService.createPay(orderId = event.orderId)
        }
    }
}
