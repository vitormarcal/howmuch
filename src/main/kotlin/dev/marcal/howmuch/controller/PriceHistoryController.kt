package dev.marcal.howmuch.controller

import dev.marcal.howmuch.service.PriceHistory
import dev.marcal.howmuch.service.PriceHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/price-history")
class PriceHistoryController(val priceHistoryService: PriceHistoryService) {

    @GetMapping("/{productName}")
    fun getPriceHistory(@PathVariable productName: String): List<PriceHistory> {
        return priceHistoryService.getPriceHistory(productName)
    }
}
