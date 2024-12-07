package dev.marcal.howmuch.service

import dev.marcal.howmuch.controller.dto.CouponDTO
import dev.marcal.howmuch.database.CouponRepository
import dev.marcal.howmuch.database.ItemRepository
import dev.marcal.howmuch.database.entity.Coupon
import org.springframework.stereotype.Service

@Service
class CouponService(private val couponRepository: CouponRepository, private val itemRepository: ItemRepository) {

    fun createCoupon(couponDTO: CouponDTO): Coupon {
        val coupon = couponRepository.save(couponDTO.toEntity())
        couponDTO.items.forEach { itemDTO ->
            itemRepository.save(itemDTO.toEntity(coupon))
        }
        return coupon
    }

    fun listCoupons(): List<Coupon> = couponRepository.findAll()
}
