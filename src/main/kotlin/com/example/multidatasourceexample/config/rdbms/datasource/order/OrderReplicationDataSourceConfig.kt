package com.example.multidatasourceexample.config.rdbms.datasource.order

import com.example.multidatasourceexample.config.rdbms.datasource.ReplicationDataSourceType
import com.example.multidatasourceexample.config.rdbms.datasource.ReplicationRoutingDataSource
import com.example.multidatasourceexample.config.rdbms.datasource.order.property.OrderReadProperty
import com.example.multidatasourceexample.config.rdbms.datasource.order.property.OrderWriteProperty
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(value = [OrderWriteProperty::class, OrderReadProperty::class])
@Profile(value = ["prod"])
class OrderReplicationDataSourceConfig(
    private val orderWriteProperty: OrderWriteProperty,
    private val orderReadProperty: OrderReadProperty,
) {

    @Bean
    fun orderWriteDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.poolName = "OrderWriteDataSourcePool"
        hikariDataSource.driverClassName = orderWriteProperty.driverClassName
        hikariDataSource.jdbcUrl = orderWriteProperty.jdbcUrl
        hikariDataSource.minimumIdle = orderWriteProperty.minimumIdle
        hikariDataSource.maximumPoolSize = orderWriteProperty.maximumPoolSize
        hikariDataSource.maxLifetime = orderWriteProperty.maxLifetime
        hikariDataSource.connectionTimeout = orderWriteProperty.connectionTimeout
        return hikariDataSource
    }

    @Bean
    fun orderReadDataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.poolName = "OrderReadDataSourcePool"
        hikariDataSource.driverClassName = orderReadProperty.driverClassName
        hikariDataSource.jdbcUrl = orderReadProperty.jdbcUrl
        hikariDataSource.minimumIdle = orderReadProperty.minimumIdle
        hikariDataSource.maximumPoolSize = orderReadProperty.maximumPoolSize
        hikariDataSource.maxLifetime = orderReadProperty.maxLifetime
        hikariDataSource.connectionTimeout = orderReadProperty.connectionTimeout
        return hikariDataSource
    }

    @Bean
    fun routingDataSource(): DataSource {
        val dataSourceMap: MutableMap<Any, Any> = HashMap()
        val orderWriteDataSource = orderWriteDataSource()
        val orderReadDataSource = orderReadDataSource()

        dataSourceMap[ReplicationDataSourceType.WRITE] = orderWriteDataSource
        dataSourceMap[ReplicationDataSourceType.READ] = orderReadDataSource

        val replicationRoutingDataSource = ReplicationRoutingDataSource()
        replicationRoutingDataSource.setTargetDataSources(dataSourceMap)
        replicationRoutingDataSource.setDefaultTargetDataSource(orderWriteDataSource)

        return replicationRoutingDataSource
    }

    @Bean
    @Primary
    fun orderDataSource(): DataSource {
        return LazyConnectionDataSourceProxy(routingDataSource())
    }
}
