package com.example.multidatasourceexample.domain.order.entity

import com.example.multidatasourceexample.common.constants.ExceptionMessage
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.Objects
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "orders")
@DynamicUpdate
class Order private constructor(
    memberId: Long,
    status: OrderStatus,
    orderedAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = status
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var orderedAt: LocalDateTime = orderedAt
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var updatedAt: LocalDateTime = updatedAt
        protected set

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    val orderItems: MutableList<OrderItem> = mutableListOf()

    override fun hashCode(): Int = Objects.hash(id)
    override fun toString(): String =
        "Order(id=$id, memberId=$memberId, status=$status, orderedAt=$orderedAt, updatedAt=$updatedAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherOrder = (other as? Order) ?: return false
        return this.id == otherOrder.id
    }

    private fun changeId(id: Long) {
        this.id = id
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.changeOrder(order = this)
    }

    fun changeStatus(status: OrderStatus) {
        if (this.status.isCanceled()) {
            throw IllegalStateException(ExceptionMessage.ORDER_STATUS_IS_CANCEL)
        }

        if (this.status.isFailed()) {
            throw IllegalStateException(ExceptionMessage.ORDER_STATUS_IS_FAIL)
        }

        this.status = status
        this.updatedAt = LocalDateTime.now()
    }

    companion object {
        fun create(memberId: Long, orderItems: List<OrderItem>): Order {
            val order = Order(
                memberId = memberId,
                status = OrderStatus.WAIT,
                orderedAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )

            orderItems.forEach { order.addOrderItem(orderItem = it) }

            return order
        }

        fun create(id: Long, memberId: Long, orderItems: List<OrderItem>): Order {
            val order = Order(
                memberId = memberId,
                status = OrderStatus.WAIT,
                orderedAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            )
            order.changeId(id = id)

            orderItems.forEach { order.addOrderItem(orderItem = it) }

            return order
        }
    }
}
