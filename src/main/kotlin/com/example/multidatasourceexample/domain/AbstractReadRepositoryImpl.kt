package com.example.multidatasourceexample.domain

abstract class AbstractReadRepositoryImpl {
    private val defaultLimit = 10L
    private val defaultOffset = 0L

    protected fun getLimit(limit: Int): Long = if (limit > 0) limit.toLong() else defaultLimit
    protected fun getOffset(offset: Int): Long = if (offset >= 0) offset.toLong() else defaultOffset
}
