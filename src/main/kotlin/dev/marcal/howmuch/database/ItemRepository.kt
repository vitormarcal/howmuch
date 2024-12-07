package dev.marcal.howmuch.database

import dev.marcal.howmuch.database.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemRepository : JpaRepository<Item, Long> {

    @Query(value = "SELECT i from Item i where i.description like %:description%")
    fun findByDescriptionLike(@Param("description") description: String): List<Item>

}