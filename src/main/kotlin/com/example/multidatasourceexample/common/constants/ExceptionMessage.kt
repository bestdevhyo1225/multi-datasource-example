package com.example.multidatasourceexample.common.constants

object ExceptionMessage {
    const val ORDER_STATUS_IS_CANCEL = "주문 취소 상태에서는 다른 상태로 변경할 수 없습니다."
    const val ORDER_STATUS_IS_FAIL = "주문 실패 상태에서는 다른 상태로 변경할 수 없습니다."
    const val ORDER_STATUS_NOT_EXISTS = "존재하지 않는 주문 상태입니다."
    const val ORDER_ITEM_CATEGORY_NOT_EXISTS = "존재하지 않는 주문 아이템 카테고리입니다."
    const val ORDER_NOT_FOUND = "해당 주문이 존재하지 않습니다."
}
