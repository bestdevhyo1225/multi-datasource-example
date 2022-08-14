package com.example.multidatasourceexample.service.dto

data class FindListResultDto<out T : Any>(
    val items: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val total: Long,
)
