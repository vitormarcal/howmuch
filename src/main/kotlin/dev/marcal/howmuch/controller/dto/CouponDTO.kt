package dev.marcal.howmuch.controller.dto

import dev.marcal.howmuch.database.entity.Coupon
import dev.marcal.howmuch.database.entity.Item

data class CouponDTO(
    val store: String,
    val cnpj: String,
    val dateTime: String,
    val accessKey: String,
    val items: List<ItemDTO>
) {
    fun toEntity() = Coupon(
        store = store,
        cnpj = cnpj,
        dateTime = dateTime
    )
}

data class ItemDTO(
    val description: String,
    val quantity: Double,
    val unitPrice: Double,
    val totalPrice: Double,
    val id: String
) {
    fun toEntity(coupon: Coupon) = Item(
        coupon = coupon,
        description = description,
        quantity = quantity,
        unitPrice = unitPrice,
        totalPrice = totalPrice,
        externalId = id
    )
}
