package dev.marcal.howmuch.service

import dev.marcal.howmuch.database.ItemRepository
import org.springframework.stereotype.Service

@Service
class PriceHistoryService(
    private val itemRepository: ItemRepository
) {

    fun getPriceHistory(productName: String): List<PriceHistory> {

        return itemRepository.findByDescriptionLike(productName).map {
            PriceHistory(
                description= it.description,
                date = it.coupon.dateTime, value = it.unitPrice, store = it.coupon.store)
        }

    }
}

data class PriceHistory(val date: String, val value: Double, val store: String, val description: String)
