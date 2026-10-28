package com.example.finalproject.data.remote.api

import com.example.finalproject.data.remote.dto.product.ProductCreateDTO
import com.example.finalproject.data.remote.dto.product.ProductResponseDTO
import com.example.finalproject.data.remote.dto.product.ProductUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {

    @GET("products/user/{idUser}")
    suspend fun getAllProductsByUser (@Path("idUser") idUser: String): Response<List<ProductResponseDTO>>

    @GET("products/category/{idCategory}/user/{idUser}")
    suspend fun getProductsByCategoryAndUser (@Path("idCategory") idCategory: String, @Path("idUser") idUser: String): Response<List<ProductResponseDTO>>

    @POST("products")
    suspend fun createProduct (@Body request: ProductCreateDTO): Response<ProductResponseDTO>

    @PUT("products")
    suspend fun updateProduct (@Body request: ProductUpdateDTO): Response<ProductResponseDTO>

    @DELETE("products/{id}")
    suspend fun deleteProduct (@Path("id") idProduct: String): Response<Unit>
}