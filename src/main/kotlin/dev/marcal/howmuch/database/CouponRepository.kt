package dev.marcal.howmuch.database

import dev.marcal.howmuch.database.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long>