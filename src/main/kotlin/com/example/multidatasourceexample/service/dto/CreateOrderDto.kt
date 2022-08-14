package com.example.multidatasourceexample.service.dto

data class CreateOrderDto(
    val memberId: Long,
    val createOrderItemsDto: CreateOrderItemsDto,
)
