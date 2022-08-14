package com.example.multidatasourceexample.domain.pay.repository

import com.example.multidatasourceexample.domain.pay.entity.Pay
import org.springframework.data.jpa.repository.JpaRepository

interface PayRepository : JpaRepository<Pay, Long>
