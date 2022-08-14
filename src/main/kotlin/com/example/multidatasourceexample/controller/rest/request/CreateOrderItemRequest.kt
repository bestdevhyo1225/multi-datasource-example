package com.example.multidatasourceexample.controller.rest.request

import javax.validation.constraints.NotBlank

data class CreateOrderItemRequest(

    @field:NotBlank(message = "category를 입력하세요.")
    val category: String,

    @field:NotBlank(message = "itemName을 입력하세요.")
    val itemName: String,

    @field:NotBlank(message = "purchasePrice를 입력하세요.")
    val purchasePrice: String,
)
