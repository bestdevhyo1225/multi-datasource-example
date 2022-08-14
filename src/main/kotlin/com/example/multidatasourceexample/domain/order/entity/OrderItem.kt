package com.example.multidatasourceexample.domain.order.entity

import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "orders_items")
@DynamicUpdate
class OrderItem private constructor(
    category: OrderItemCategory,
    itemName: String,
    purchasePrice: Float,
    createdAt: LocalDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var category: OrderItemCategory = category
        protected set

    @Column(nullable = false)
    var itemName: String = itemName
        protected set

    @Column(nullable = false)
    var purchasePrice: Float = purchasePrice
        protected set

    @Column(nullable = false, columnDefinition = "DATETIME")
    var createdAt: LocalDateTime = createdAt
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    var order: Order? = null
        protected set

    override fun hashCode(): Int = Objects.hash(id)
    override fun toString(): String =
        "OrderItem(id=$id, category=$category, itemName=$itemName, purchasePrice=$purchasePrice, createdAt=$createdAt)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherOrderItem = (other as? OrderItem) ?: return false
        return this.id == otherOrderItem.id &&
            this.category == otherOrderItem.category &&
            this.itemName == otherOrderItem.itemName &&
            this.purchasePrice == otherOrderItem.purchasePrice &&
            this.createdAt == otherOrderItem.createdAt
    }

    private fun changeId(id: Long) {
        this.id = id
    }

    fun changeOrder(order: Order) {
        this.order = order
    }

    companion object {
        fun create(category: String, itemName: String, purchasePrice: Float) = OrderItem(
            category = OrderItemCategory.convert(value = category),
            itemName = itemName,
            purchasePrice = purchasePrice,
            createdAt = LocalDateTime.now(),
        )

        fun create(category: String, id: Long, itemName: String, purchasePrice: Float): OrderItem {
            val orderItem = OrderItem(
                category = OrderItemCategory.convert(value = category),
                itemName = itemName,
                purchasePrice = purchasePrice,
                createdAt = LocalDateTime.now(),
            )
            orderItem.changeId(id = id)
            return orderItem
        }
    }
}
