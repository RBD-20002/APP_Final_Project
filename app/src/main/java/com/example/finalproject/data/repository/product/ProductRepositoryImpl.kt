package com.example.finalproject.data.repository.product

import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.remote.api.ProductApiService
import com.example.finalproject.data.remote.dto.product.ProductCreateDTO
import com.example.finalproject.data.remote.dto.product.ProductResponseDTO
import com.example.finalproject.data.remote.dto.product.ProductUpdateDTO
import okio.IOException

class ProductRepositoryImpl (
    private val productApiService: ProductApiService,
    private val sessionDataStore: SessionDataStore
): ProductRepository {

    override suspend fun getProductByUser(): Result<List<ProductResponseDTO>> {
        return try {
            val idUser = sessionDataStore.getUserId() ?: return Result.failure(Exception("Sesion no iniciada"))
            val response = productApiService.getAllProductsByUser(idUser = idUser)

            if( response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al obtener los productos"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductByCategory(idCategory: String): Result<List<ProductResponseDTO>> {
        return try {
            val idUser = sessionDataStore.getUserId() ?: return Result.failure(Exception("Sesion no iniciada"))
            val response = productApiService.getProductsByCategoryAndUser(idCategory = idCategory, idUser = idUser)

            if(response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al obtener los productos"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createProduct(product: ProductCreateDTO): Result<ProductResponseDTO> {
        return try {
            val response = productApiService.createProduct(product)

            if(response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al crear el producto"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduct(product: ProductUpdateDTO): Result<ProductResponseDTO> {
        return try {
            val response = productApiService.updateProduct(product)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(
                    Exception(
                        response.errorBody()?.string() ?: "Error al actualizar el producto"
                    )
                )
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(idProduct: String): Result<Unit> {
        return try {
            val response = productApiService.deleteProduct(idProduct = idProduct)
            if(response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al eliminar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}