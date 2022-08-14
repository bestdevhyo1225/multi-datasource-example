package com.example.multidatasourceexample.controller.rest.response

data class ErrorResponse(
    val status: String = "error",
    val message: String,
)
