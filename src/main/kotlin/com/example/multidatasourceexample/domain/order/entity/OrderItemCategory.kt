package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage

enum class OrderItemCategory(val label: String) {
    BOOK("책");

    companion object {
        fun convert(value: String): OrderItemCategory {
            return try {
                OrderItemCategory.valueOf(value.uppercase())
            } catch (exception: Exception) {
                throw IllegalArgumentException(ExceptionMessage.ORDER_ITEM_CATEGORY_NOT_EXISTS)
            }
        }
    }
}
