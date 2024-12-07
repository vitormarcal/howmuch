package dev.marcal.howmuch.service

import jakarta.annotation.PostConstruct
import net.sourceforge.tess4j.ITesseract
import net.sourceforge.tess4j.Tesseract
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists

@Service
class OcrService(
    @Value("\${app.volume-dir}") volumeAppDir: String,
    @Value("\${app.ocr-language}") private val ocrLanguage: String
) {
    private val tessDataDir = (volumeAppDir + "/tessdata").apply {
        Paths.get(this).takeIf { !it.exists() }?.run { Files.createDirectory(this) }
    }
    private val tesseract: ITesseract = Tesseract().apply {
        setDatapath(tessDataDir)
        setLanguage(ocrLanguage)  // Default language is Portuguese
    }

    private fun downloadLanguage(languageCode: String) {
        val languageFile = File("$tessDataDir/$languageCode.traineddata")

        if (!languageFile.exists()) {
            println("Downloading language file for $languageCode...")
            try {
                val url = URL("https://github.com/tesseract-ocr/tessdata/raw/main/$languageCode.traineddata")
                val inputStream = url.openStream()
                val outputPath: Path = File(tessDataDir).toPath().resolve("$languageCode.traineddata")
                Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING)
                println("Language $languageCode downloaded successfully!")
            } catch (e: Exception) {
                println("Error downloading language $languageCode: ${e.message}")
            }
        } else {
            println("Language $languageCode is already installed.")
        }
    }

    fun recognizeTextFromImage(imageFile: File, languageCode: String? = null): String {
        downloadLanguage(languageCode ?: ocrLanguage)

        return try {
            tesseract.setLanguage(languageCode)
            tesseract.doOCR(imageFile)
        } catch (e: Exception) {
            "Error performing OCR: ${e.message}"
        }
    }
}
