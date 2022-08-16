package com.example.multidatasourceexample.controller.rest.request

import com.example.multidatasourceexample.service.dto.CreateOrderItemDto
import javax.validation.constraints.NotBlank

data class CreateOrderItemRequest(

    @field:NotBlank(message = "category를 입력하세요.")
    val category: String,

    @field:NotBlank(message = "itemName을 입력하세요.")
    val itemName: String,

    @field:NotBlank(message = "purchasePrice를 입력하세요.")
    val purchasePrice: String,
) {
    fun toServiceDto(): CreateOrderItemDto = with(receiver = this) {
        CreateOrderItemDto(
            category = category,
            itemName = itemName,
            purchasePrice = purchasePrice.toFloat(),
        )
    }
}
