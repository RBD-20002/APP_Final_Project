package com.example.finalproject.data.remote.api

import com.example.finalproject.data.remote.dto.category.CategoryCreateDTO
import com.example.finalproject.data.remote.dto.category.CategoryResponseDTO
import com.example.finalproject.data.remote.dto.category.CategoryUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApiService {

    @GET("category/user/{idUser}")
    suspend fun getCategoriesByUser (@Path("idUser") idUser: String): Response<List<CategoryResponseDTO>>

    @POST("category")
    suspend fun createCategory (@Body request: CategoryCreateDTO): Response<CategoryResponseDTO>

    @PUT("category")
    suspend fun updateCategory (@Body request: CategoryUpdateDTO): Response<CategoryResponseDTO>

    @GET("category/{id}")
    suspend fun getCategoryById (@Path("id") idCategory: String): Response<CategoryResponseDTO>
}