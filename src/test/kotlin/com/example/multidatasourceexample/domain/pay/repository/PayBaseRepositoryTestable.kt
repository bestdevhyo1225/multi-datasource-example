package com.example.multidatasourceexample.domain.pay.repository

import com.example.multidatasourceexample.config.rdbms.datasource.pay.PayDataSourceConfig
import com.example.multidatasourceexample.config.rdbms.jpa.JpaConfiguration
import com.example.multidatasourceexample.config.rdbms.jpa.pay.PayJpaQueryFactoryConfig
import com.example.multidatasourceexample.config.rdbms.jpa.pay.PayTransactionManagerConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(
    classes = [
        // Config
        JpaConfiguration::class,
        PayDataSourceConfig::class,
        PayTransactionManagerConfig::class,
        PayJpaQueryFactoryConfig::class,
        // Pay Repository
        PayRepository::class,
    ],
)
interface PayBaseRepositoryTestable
