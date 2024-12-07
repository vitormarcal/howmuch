package dev.marcal.howmuch.service

import dev.marcal.howmuch.controller.dto.CouponDTO
import dev.marcal.howmuch.controller.dto.ItemDTO
import org.springframework.stereotype.Service

@Service
class CouponParsingService {

    fun parseNFCe(ocrText: String): CouponDTO {
        val lines = ocrText.lines()

        // Extract store name and CNPJ
        // Regex to capture the store name (first non-empty line)
        val store = lines.find { it.isNotBlank() } ?: "Unknown Store"

        // Regex to capture CNPJ
        val cnpj = Regex("""\d{2}\.\d{3}\.\d{3}/\d{4}-\d{2}""")
            .find(ocrText)?.value ?: "Unknown CNPJ"

        // Regex to capture date and time in the format DD/MM/YYYY HH:MM
        val dateTime = Regex("""\d{2}/\d{2}/\d{4} \d{2}:\d{2}""")
            .find(ocrText)?.value ?: "Unknown DateTime"

        // Regex to capture access key (44 consecutive digits)
        val accessKey = Regex("""\d{44}""").find(ocrText)?.value ?: "Unknown AccessKey"

        // Regex adapted to identify items in the text
        val itemPattern = Regex(
            """\d+\s+(.+?)\n([\d.,]+)\s+x\s+([\d.,]+)\s+([\d.,]+)"""
        )

        // Process items using the regex
        val items = itemPattern.findAll(ocrText).mapIndexed { index, match ->
            val description = match.groupValues[1].trim()
            val quantity = match.groupValues[2].replace(",", ".").toDouble()
            val unitPrice = match.groupValues[3].replace(",", ".").toDouble()
            val totalPrice = match.groupValues[4].replace(",", ".").toDouble()

            ItemDTO(
                description = description,
                quantity = quantity,
                unitPrice = unitPrice,
                totalPrice = totalPrice,
                id = (index + 1).toString() // Generate sequential ID
            )
        }.toList()

        // Return the coupon DTO
        return CouponDTO(
            store = store,
            cnpj = cnpj,
            dateTime = dateTime,
            accessKey = accessKey,
            items = items
        )
    }
}
