package com.example.multidatasourceexample.config.rdbms.datasource.order

import com.example.multidatasourceexample.config.rdbms.datasource.order.property.OrderBasicProperty
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(value = [OrderBasicProperty::class])
@Profile(value = ["test", "dev"])
class OrderDataSourceConfig(
    private val orderBasicProperty: OrderBasicProperty,
) {

    @Bean
    fun orderDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = orderBasicProperty.driverClassName
        hikariDataSource.jdbcUrl = orderBasicProperty.jdbcUrl
        hikariDataSource.minimumIdle = orderBasicProperty.minimumIdle
        hikariDataSource.maximumPoolSize = orderBasicProperty.maximumPoolSize
        hikariDataSource.maxLifetime = orderBasicProperty.maxLifetime
        hikariDataSource.connectionTimeout = orderBasicProperty.connectionTimeout
        return hikariDataSource
    }
}
