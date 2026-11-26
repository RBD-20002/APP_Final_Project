package com.example.finalproject.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.finalproject.data.remote.dto.product.ProductCreateDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class CsvImportUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(uri: Uri, idCategory: String, idUser: String): Result<List<ProductCreateDTO>> = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw Exception("No se pudo abrir el archivo seleccionado.")

            val productsToCreate = mutableListOf<ProductCreateDTO>()
            val reader = BufferedReader(InputStreamReader(inputStream))
            var lineNumber = 0

            reader.useLines { lines ->
                lines.forEach { line ->
                    lineNumber++

                    if (lineNumber == 1 && (line.contains("NOMBRE", ignoreCase = true) || line.contains("NAME", ignoreCase = true))) {
                        return@forEach
                    }

                    if (line.isBlank()) return@forEach

                    val separator = if (line.contains(";")) ";" else ","
                    val tokens = line.split(separator)

                    if (tokens.size < 4) {
                        throw Exception("Error en la fila $lineNumber: Faltan columnas. Se esperaban al menos 4.")
                    }

                    val name = tokens[0].trim()
                    if (name.isBlank()) throw Exception("Error en la fila $lineNumber: El nombre está vacío.")

                    val amount = tokens[1].trim().toIntOrNull()
                        ?: throw Exception("Error en la fila $lineNumber: La cantidad no es válida.")

                    val priceStr = tokens[2].trim().replace(",", ".")
                    val price = priceStr.toDoubleOrNull()
                        ?: throw Exception("Error en la fila $lineNumber: El precio no es válido.")

                    val market = tokens[3].trim()
                    if (market.isBlank()) throw Exception("Error en la fila $lineNumber: La tienda está vacía.")

                    var status = "NONE"
                    if (tokens.size > 4 && tokens[4].isNotBlank()) {
                        val parsedStatus = tokens[4].trim().uppercase()
                        if (parsedStatus in listOf("SAVED", "BORROWED", "GUARDADO", "PRESTADA", "NINGUNO")) {
                            status = if (parsedStatus == "GUARDADO") "SAVED"
                            else if (parsedStatus == "PRESTADA") "BORROWED"
                            else parsedStatus
                        }
                    }

                    val dynamicMap = mutableMapOf<String, JsonPrimitive>()
                    if (tokens.size > 5 && tokens[5].isNotBlank()) {
                        val attributes = tokens[5].split("|")
                        attributes.forEach { attr ->
                            val kv = attr.split(":")
                            if (kv.size == 2) {
                                val key = kv[0].trim()
                                val value = kv[1].trim()
                                if (key.isNotBlank() && value.isNotBlank()) {
                                    dynamicMap[key] = JsonPrimitive(value)
                                }
                            }
                        }
                    }

                    productsToCreate.add(
                        ProductCreateDTO(
                            proName = name,
                            proAmount = amount,
                            proPrice = price,
                            proMarketName = market,
                            proStatus = status,
                            imageUrl = "",
                            idCategory = idCategory,
                            idUser = idUser,
                            dynamicAttributes = dynamicMap
                        )
                    )
                }
            }

            Result.success(productsToCreate)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}