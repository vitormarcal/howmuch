package dev.marcal.howmuch.config

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.exists

@Configuration
class AppDirConfig(@Value("\${app.volume-dir}") private val volumeAppDir: String) {

    private val logger = LoggerFactory.getLogger(AppDirConfig::class.java)

    @PostConstruct
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun init() {
        try {
            Paths.get(volumeAppDir).takeIf { !it.exists() }?.run {
                Files.createDirectory(Paths.get(volumeAppDir))
            }
        } catch (e: Exception) {
            logger.error("Failed to create directory $volumeAppDir", e)
            throw e
        }
    }
}
