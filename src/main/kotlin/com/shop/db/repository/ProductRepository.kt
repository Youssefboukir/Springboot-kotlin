package com.shop.db.repository

import com.shop.db.entity.ProductEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CrudRepository<ProductEntity, String> {

    fun findBySku(sku: String): ProductEntity?

    fun existsBySku(sku:String): Boolean

    @Query("FROM ProductEntity WHERE sku IN :skus")
    fun findAllBySkus(@Param("skus") skus: List<String>): List<ProductEntity>
}