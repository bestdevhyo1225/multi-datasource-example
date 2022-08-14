package com.example.multidatasourceexample.config.rdbms.datasource.pay

import com.example.multidatasourceexample.config.rdbms.datasource.pay.property.PayBasicProperty
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(value = [PayBasicProperty::class])
@Profile(value = ["test", "dev", "prod"])
class PayDataSourceConfig(
    private val payBasicProperty: PayBasicProperty,
) {

    @Bean
    fun payDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = payBasicProperty.driverClassName
        hikariDataSource.jdbcUrl = payBasicProperty.jdbcUrl
        hikariDataSource.minimumIdle = payBasicProperty.minimumIdle
        hikariDataSource.maximumPoolSize = payBasicProperty.maximumPoolSize
        hikariDataSource.maxLifetime = payBasicProperty.maxLifetime
        hikariDataSource.connectionTimeout = payBasicProperty.connectionTimeout
        return hikariDataSource
    }
}
