package dev.marcal.howmuch.controller

import dev.marcal.howmuch.controller.dto.CouponDTO
import dev.marcal.howmuch.database.entity.Coupon
import dev.marcal.howmuch.service.CouponService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coupons")
class CouponController(private val couponService: CouponService) {

    @PostMapping
    fun createCoupon(@RequestBody couponDTO: CouponDTO): Coupon = couponService.createCoupon(couponDTO)

    @GetMapping
    fun listCoupons(): List<Coupon> = couponService.listCoupons()
}
