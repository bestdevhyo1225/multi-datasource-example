package com.example.multidatasourceexample.service.pay

import org.springframework.stereotype.Service

@Service
class PayExternalApiCallService {

    fun execute() {
        Thread.sleep(1_000)
    }
}
