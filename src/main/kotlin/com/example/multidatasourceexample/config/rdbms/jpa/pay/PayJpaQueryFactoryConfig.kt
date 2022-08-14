package com.example.multidatasourceexample.config.rdbms.jpa.pay

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class PayJpaQueryFactoryConfig {

    @PersistenceContext(unitName = "payEntityManager")
    private lateinit var payEntityManager: EntityManager

    @Bean
    fun payEntityManager(): JPAQueryFactory {
        return JPAQueryFactory(payEntityManager)
    }
}
