package com.example.multidatasourceexample.domain.pay.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "pays")
@DynamicUpdate
class Pay private constructor(
    orderId: Long,
    status: PayStatus,
    createdAt: LocalDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(nullable = false)
    var orderId: Long = orderId
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PayStatus = status
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime = createdAt
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set

    override fun hashCode(): Int = Objects.hash(id)
    override fun toString(): String =
        "Pay(id=$id, orderId=$orderId, status=$status, createdAt=$createdAt, updatedAt=$updatedAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherPay = (other as? Pay) ?: return false
        return this.id == otherPay.id &&
            this.orderId == otherPay.orderId &&
            this.status == otherPay.status &&
            this.createdAt == otherPay.createdAt &&
            this.updatedAt == otherPay.updatedAt
    }

    private fun changeId(id: Long) {
        this.id = id
    }

    fun changeStatus(status: PayStatus) {
        if (this.status.isCanceled()) {
            throw IllegalStateException(ExceptionMessage.PAY_STATUS_IS_CANCEL)
        }

        if (this.status.isFailed()) {
            throw IllegalStateException(ExceptionMessage.PAY_STATUS_IS_FAIL)
        }

        this.status = status
        this.updatedAt = LocalDateTime.now()
    }

    companion object {
        fun create(orderId: Long) = Pay(orderId = orderId, status = PayStatus.WAIT, createdAt = LocalDateTime.now())

        fun create(id: Long, orderId: Long): Pay {
            val pay = Pay(orderId = orderId, status = PayStatus.WAIT, createdAt = LocalDateTime.now())
            pay.changeId(id = id)
            return pay
        }
    }
}
