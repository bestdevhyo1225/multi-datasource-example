package com.example.multidatasourceexample.controller.rest

import com.example.multidatasourceexample.controller.rest.response.SuccessResponse
import com.example.multidatasourceexample.service.dto.FindListResultDto
import com.example.multidatasourceexample.service.dto.FindOrderResultDto
import com.example.multidatasourceexample.service.order.OrderReadService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/orders")
@Validated
class OrderReadController(
    private val orderReadService: OrderReadService,
) {

    @GetMapping
    fun findOrders(
        @Positive @RequestParam(defaultValue = "1")
        pageNumber: Int,
        @Positive @RequestParam(defaultValue = "10")
        pageSize: Int,
    ): ResponseEntity<SuccessResponse<FindListResultDto<FindOrderResultDto>>> =
        ok(SuccessResponse(data = orderReadService.findOrders(pageNumber = pageNumber, pageSize = pageSize)))

    @GetMapping("/{id}")
    fun findOrder(
        @Positive @PathVariable
        id: Long,
    ): ResponseEntity<SuccessResponse<FindOrderResultDto>> =
        ok(SuccessResponse(data = orderReadService.findOrderWithOrderItem(id = id)))
}
