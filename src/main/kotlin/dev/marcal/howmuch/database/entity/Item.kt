package dev.marcal.howmuch.database.entity

import jakarta.persistence.*

@Entity
data class Item(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    val coupon: Coupon,
    val description: String,
    val quantity: Double,
    val unitPrice: Double,
    val totalPrice: Double,
    val externalId: String
)
