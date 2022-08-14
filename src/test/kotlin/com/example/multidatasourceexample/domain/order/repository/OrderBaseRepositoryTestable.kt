package com.example.multidatasourceexample.domain.order.repository

import com.example.multidatasourceexample.config.rdbms.datasource.order.OrderDataSourceConfig
import com.example.multidatasourceexample.config.rdbms.jpa.JpaConfiguration
import com.example.multidatasourceexample.config.rdbms.jpa.order.OrderJpaQueryFactoryConfig
import com.example.multidatasourceexample.config.rdbms.jpa.order.OrderTransactionManagerConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(
    classes = [
        // Config
        JpaConfiguration::class,
        OrderDataSourceConfig::class,
        OrderTransactionManagerConfig::class,
        OrderJpaQueryFactoryConfig::class,
        // Order Repository
        OrderRepository::class,
        OrderReadRepository::class,
        OrderReadRepositoryImpl::class,
    ],
)
internal interface OrderBaseRepositoryTestable
