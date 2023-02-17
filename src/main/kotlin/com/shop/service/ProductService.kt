package com.shop.service

import com.shop.db.entity.ProductEntity
import com.shop.db.repository.ProductRepository
import com.shop.domain.product.ProductResponse
import com.shop.domain.product.ProductResponse.Companion.toProductResponse
import com.shop.exception.ProductNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        // find a product by its SKU and throw an exception if the product is not found
        return productRepository.findBySku(sku)?.toProductResponse()?: throw ProductNotFoundException("Product with sku "+sku+" not found")
    }

    fun findAllProductBySkus(skus:List<String>):List<ProductResponse>{
        // find a list of products by their skus
        var listProduct: List<ProductEntity> = productRepository.findAllBySkus(skus)
        // check if the list of products is empty
        if(listProduct.size==0){
            // throw an exception if the list of products is empty
            throw ProductNotFoundException("Product with sku "+skus+" not found")
        }
        // convert the list of products to a list of ProductResponse objects and return it
        return convertProductToProductResponse(listProduct)
    }

    fun create(product: ProductResponse): ProductResponse {
        // check if a product with the same SKU already exists
        if(productRepository.existsBySku(product.sku)){
            // throw an exception
            throw IllegalStateException("Product with the same sku already exists")
        }
        // create a new ProductEntity object using the provided product information
        var productAdd = ProductEntity (sku=product.sku,name=product.name,description=product.description,price=product.price,stock = product.stock)
        // save the new product to the repository and return its result as a ProductResponse object
        return productRepository.save(productAdd).toProductResponse()
    }

    fun update(newProduct: ProductResponse, sku: String): ProductResponse {
        // find a product by its SKU and throw an exception if the product is not found
        var product : ProductEntity = productRepository.findBySku(sku)?: throw ProductNotFoundException("Product with sku "+sku+" not found")
        // create a copy of the found product with updated name, description, and price
        var productUpdated : ProductEntity = product.copy(name=newProduct.name,description=newProduct.description,price=newProduct.price)
        // save the updated product to the repository and return its result as a ProductResponse object
        return productRepository.save(productUpdated).toProductResponse()
    }

    fun convertProductToProductResponse(products:List<ProductEntity>): List<ProductResponse> {
        // map the list of products to a list of ProductResponse objects
        return products.map{ ProductResponse(it.sku,it.name,it.description?: "",it.price,it.stock) }
    }

}
