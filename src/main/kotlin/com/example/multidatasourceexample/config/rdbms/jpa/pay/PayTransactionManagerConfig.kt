package com.example.multidatasourceexample.config.rdbms.jpa.pay

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EntityScan(value = ["com.example.multidatasourceexample.domain.pay.entity"])
@EnableJpaRepositories(
    basePackages = ["com.example.multidatasourceexample.domain.pay.repository"],
    entityManagerFactoryRef = "payEntityManagerFactory",
    transactionManagerRef = "payTransactionManager",
)
class PayTransactionManagerConfig(
    @Value("\${spring.jpa.open-in-view}")
    private val openInView: Boolean,
) {

    @Bean
    fun payEntityManagerFactory(
        @Qualifier("payDataSource")
        dataSource: DataSource,
        @Qualifier("jpaProperties")
        jpaProperties: JpaProperties,
    ): LocalContainerEntityManagerFactoryBean {
        val properties = jpaProperties.properties.toProperties()
        properties["open-in-view"] = openInView

        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.persistenceUnitName = "payEntityManager"
        entityManagerFactoryBean.setPackagesToScan("com.example.multidatasourceexample.domain.pay")
        entityManagerFactoryBean.setJpaProperties(properties)

        return entityManagerFactoryBean
    }

    @Bean
    fun payTransactionManager(
        @Qualifier("payDataSource")
        dataSource: DataSource,
        @Qualifier("jpaProperties")
        jpaProperties: JpaProperties,
    ): PlatformTransactionManager {
        val jpaTransactionManager = JpaTransactionManager()
        jpaTransactionManager.entityManagerFactory =
            payEntityManagerFactory(dataSource = dataSource, jpaProperties = jpaProperties).`object`

        return jpaTransactionManager
    }
}
