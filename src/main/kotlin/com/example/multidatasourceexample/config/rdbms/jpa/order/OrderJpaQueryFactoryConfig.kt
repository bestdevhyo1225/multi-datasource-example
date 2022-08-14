package com.example.multidatasourceexample.config.rdbms.jpa.order

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class OrderJpaQueryFactoryConfig {

    @PersistenceContext(unitName = "orderEntityManager")
    private lateinit var orderEntityManager: EntityManager

    @Bean(name = ["orderJpaQueryFactory"])
    fun orderJpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(orderEntityManager)
    }
}
