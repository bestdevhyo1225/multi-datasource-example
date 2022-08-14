package com.example.multidatasourceexample.controller.rest.request

import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class CreateOrderRequest(

    @field:Positive(message = "memberId는 0보다 커야 합니다.")
    val memberId: Long,

    @field:NotEmpty(message = "orderItems를 입력하세요.")
    val orderItems: List<@Valid CreateOrderItemRequest>,
)
