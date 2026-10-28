package com.example.finalproject.data.repository.category

import com.example.finalproject.data.remote.dto.category.CategoryResponseDTO

interface CategoryRepository {
    suspend fun getCategories(): Result<List<CategoryResponseDTO>>
    suspend fun createCategory(catName: String, catIconKey: String): Result<CategoryResponseDTO>
    suspend fun updateCategory(idCategory: String, catName: String, catIconKey: String): Result<CategoryResponseDTO>
    suspend fun getCategoryById(idCategory: String): Result<CategoryResponseDTO>
}