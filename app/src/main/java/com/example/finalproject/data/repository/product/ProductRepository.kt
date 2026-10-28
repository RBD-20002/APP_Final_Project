package com.example.finalproject.data.repository.product

import com.example.finalproject.data.remote.dto.product.ProductCreateDTO
import com.example.finalproject.data.remote.dto.product.ProductResponseDTO
import com.example.finalproject.data.remote.dto.product.ProductUpdateDTO

interface ProductRepository {
    suspend fun getProductByUser(): Result<List<ProductResponseDTO>>
    suspend fun getProductByCategory(idCategory: String): Result<List<ProductResponseDTO>>
    suspend fun createProduct(product: ProductCreateDTO): Result<ProductResponseDTO>
    suspend fun updateProduct(product: ProductUpdateDTO): Result<ProductResponseDTO>
    suspend fun deleteProduct(idProduct: String): Result<Unit>
}