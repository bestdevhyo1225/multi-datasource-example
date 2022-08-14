package com.example.multidatasourceexample.domain.pay.repository

import com.example.multidatasourceexample.domain.pay.entity.Pay
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

internal class PayRepositoryTests : PayBaseRepositoryTestable, DescribeSpec() {

    override fun extensions(): List<Extension> = listOf(SpringExtension)
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    @Autowired
    private lateinit var payRepository: PayRepository

    init {
        this.describe("save 메서드는") {
            it("Pay 엔티티 저장한다.") {
                // given
                val orderId = 1L
                val pay = Pay.create(orderId = orderId)

                // when
                withContext(Dispatchers.IO) { payRepository.save(pay) }

                // then
                val findPay = payRepository.findByIdOrNull(pay.id)!!

                findPay.shouldBe(pay)
            }
        }
    }
}
