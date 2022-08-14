package com.example.multidatasourceexample.controller.rest

import com.example.multidatasourceexample.controller.rest.request.CreateOrderRequest
import com.example.multidatasourceexample.controller.rest.response.SuccessResponse
import com.example.multidatasourceexample.service.dto.CreateOrderDto
import com.example.multidatasourceexample.service.dto.CreateOrderItemDto
import com.example.multidatasourceexample.service.dto.CreateOrderItemsDto
import com.example.multidatasourceexample.service.dto.CreateOrderResultDto
import com.example.multidatasourceexample.service.order.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/orders")
@Validated
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping
    fun createOrder(@Valid @RequestBody request: CreateOrderRequest): ResponseEntity<SuccessResponse<CreateOrderResultDto>> {
        val createOrderItemsDto = CreateOrderItemsDto(
            items = request.orderItems.map {
                CreateOrderItemDto(
                    category = it.category,
                    itemName = it.itemName,
                    purchasePrice = it.purchasePrice.toFloat(),
                )
            },
        )

        val createOrderResultDto: CreateOrderResultDto = orderService.createOrder(
            dto = CreateOrderDto(
                memberId = request.memberId,
                createOrderItemsDto = createOrderItemsDto,
            ),
        )

        return created(URI.create("/orders/${createOrderResultDto.orderId}"))
            .body(SuccessResponse(data = createOrderResultDto))
    }
}
