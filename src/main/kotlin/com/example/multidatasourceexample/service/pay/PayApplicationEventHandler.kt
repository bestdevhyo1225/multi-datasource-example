package com.example.multidatasourceexample.service.pay

import com.example.multidatasourceexample.service.dto.event.FailurePaymentEventDto
import com.example.multidatasourceexample.service.dto.event.SuccessPaymentEventDto
import com.example.multidatasourceexample.service.order.OrderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PayApplicationEventHandler(
    private val orderService: OrderService,
) {

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
