package com.example.finalproject.data.repository.category

import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.remote.api.CategoryApiService
import com.example.finalproject.data.remote.dto.category.CategoryCreateDTO
import com.example.finalproject.data.remote.dto.category.CategoryResponseDTO
import com.example.finalproject.data.remote.dto.category.CategoryUpdateDTO
import okio.IOException

class CategoryRepositoryImpl (
    private val categoryApiService: CategoryApiService,
    private val sessionDataStore: SessionDataStore
): CategoryRepository{

    override suspend fun getCategories(): Result<List<CategoryResponseDTO>> {
        return try {
            val idUser = sessionDataStore.getUserId() ?: return Result.failure(Exception("Sesion no iniciada"))
            val response = categoryApiService.getCategoriesByUser(idUser)

            if(response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al obtener las categorias"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createCategory(catName: String, catIconKey: String) : Result<CategoryResponseDTO> {
        return try {
            val idUser = sessionDataStore.getUserId() ?: return Result.failure(Exception("Sesion no iniciada"))
            val request = CategoryCreateDTO(catName = catName, catIconKey = catIconKey, idUser = idUser)
            val response = categoryApiService.createCategory(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al crear la categoria"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCategory(idCategory: String, catName: String, catIconKey: String): Result<CategoryResponseDTO> {
        return try {
            val idUser = sessionDataStore.getUserId() ?: return Result.failure(Exception("Sesion no iniciada"))
            val request = CategoryUpdateDTO(idCategory = idCategory, catName = catName, catIconKey = catIconKey, idUser = idUser)
            val response = categoryApiService.updateCategory(request)

            if(response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al actualizar la categoria"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategoryById(idCategory: String): Result<CategoryResponseDTO> {
        return try {
            val response = categoryApiService.getCategoryById(idCategory)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Categoría no encontrada"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de conexión con el servidor"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}