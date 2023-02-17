package com.shop

import com.fasterxml.jackson.databind.ObjectMapper
import com.shop.controller.ProductController
import com.shop.domain.product.ProductResponse
import com.shop.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal


@WebMvcTest(ProductController::class)
class ProductControllerMockMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var productService: ProductService

    @Test
    fun testGetProductBySkus() {
        val products = listOf(
            ProductResponse("1", "Product 1", "Product 1", BigDecimal.TEN, BigDecimal.ONE),
            ProductResponse("2", "Product 2", "Product 2", BigDecimal.TEN, BigDecimal.ONE),
        )
        `when`(productService.findAllProductBySkus(listOf("1", "2"))).thenReturn(products)

        mockMvc.perform(MockMvcRequestBuilders.get("/products?skus=1,2"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].sku").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Product 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(BigDecimal.TEN))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].stock").value(BigDecimal.ONE))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].sku").value("2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(BigDecimal.TEN))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].stock").value(BigDecimal.ONE))
    }


    @Test
    fun testGetProduct() {
        val product = ProductResponse("1", "Product 2", "Product 2", BigDecimal.TEN, BigDecimal.ONE)
        `when`(productService.findProductBySku("1")).thenReturn(product)

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.sku").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.TEN))
            .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(BigDecimal.ONE))
    }

    @Test
    fun testUpdateProduct() {
        val product = ProductResponse("1", "Product 2", "Product 2", BigDecimal.TEN,BigDecimal.ONE)
        `when`(productService.update(product,"1")).thenReturn(product)
        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(product)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.sku").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Product 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.TEN))
            .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(BigDecimal.ONE))
    }

    private fun asJsonString(obj: Any): String {
        return try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}