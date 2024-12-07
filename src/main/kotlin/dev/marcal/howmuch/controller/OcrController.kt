package dev.marcal.howmuch.controller

import dev.marcal.howmuch.controller.dto.CouponDTO
import dev.marcal.howmuch.service.CouponParsingService
import dev.marcal.howmuch.service.OcrService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/ocr")
class OcrController(private val ocrService: OcrService, private val couponParsingService: CouponParsingService) {

    @PostMapping("/recognize")
    fun recognizeText(@RequestParam("image") file: MultipartFile): ResponseEntity<CouponDTO> {
        val tempFile = File.createTempFile("upload-", file.originalFilename)
        file.transferTo(tempFile)

        val coupon = ocrService.recognizeTextFromImage(tempFile).let {
            couponParsingService.parseNFCe(it)
        }

        return ResponseEntity.ok(coupon)
    }


}
