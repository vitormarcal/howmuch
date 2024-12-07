package dev.marcal.howmuch.database.entity

import jakarta.persistence.*

@Entity
data class Coupon(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val store: String,
    val cnpj: String,
    val dateTime: String
)

