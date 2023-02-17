package com.shop.controller

import com.shop.domain.product.ProductResponse
import com.shop.exception.ProductNotFoundException
import com.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    @ResponseStatus(HttpStatus.OK)
    fun findProductsBySku(@PathVariable("sku") sku: String): ResponseEntity<ProductResponse> {
        try {
            logger.info("Request for product $sku")
            // call findProductBySku from productService
            val product = productService.findProductBySku(sku)!!
            // return an OK response with the found product information
            return ResponseEntity.ok(product)
        } catch (ex: ProductNotFoundException) {
            // log an error message if the product is not found
            logger.error(ex.message)
            // throw a ResponseStatusException with a NOT_FOUND status code and the exception message
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    @GetMapping("/products" , produces = ["application/json;charset=utf-8"])
    @ResponseStatus(HttpStatus.OK)
    fun getProductsBySkus(@RequestParam skus:List<String>): ResponseEntity<List<ProductResponse>> {
        try {
            // return an OK response with the list of products found by their SKUs
            return ResponseEntity.ok(productService.findAllProductBySkus(skus))
        } catch (ex: ProductNotFoundException) {
            // log an error message if the product is not found
            logger.error(ex.message)
            // throw a ResponseStatusException with a NOT_FOUND status code and the exception message
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.localizedMessage, ex)
        }
    }

    @PostMapping("/product/add" , consumes = ["application/json;charset=utf-8"])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveProduct(@RequestBody product: ProductResponse):ResponseEntity<ProductResponse> {
        try {
            logger.info("Request for adding new product $product")
            var productResquest : ProductResponse = productService.create(product)
            // return an OK response with the new product information
            return ResponseEntity.ok(productResquest)
        } catch (ex: IllegalStateException) {
            // log an error message if the product is not found
            logger.error(ex.message)
            // throw a ResponseStatusException with a BAD_REQUEST status code and the exception message
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex)
        }
    }

    @PutMapping("/product/update/{sku}", consumes = ["application/json;charset=utf-8"])
    @ResponseStatus(HttpStatus.OK)
    fun updateProduct(@RequestBody product: ProductResponse, @PathVariable("sku") sku: String):ResponseEntity<ProductResponse> {
        try {
            logger.info("Request for updating product $product")
            // call the update method of the product service to update the product
            var productResponse: ProductResponse = productService.update(product, sku)
            // return an OK response with the updated product information
            return ResponseEntity.ok(productResponse)
        } catch (ex: ProductNotFoundException) {
            // log an error message if the product is not found
            logger.error(ex.message)
            // throw a ResponseStatusException with a BAD_REQUEST status code and the exception message
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex)
        }
    }
}
