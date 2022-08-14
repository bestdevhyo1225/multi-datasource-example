package com.example.multidatasourceexample.controller.rest.response

data class SuccessResponse<T : Any>(
    val status: String = "success",
    val data: T,
)
