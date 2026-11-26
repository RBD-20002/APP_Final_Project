package com.example.finalproject.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.finalproject.model.Product
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PdfExportUseCase @Inject constructor(
    @ApplicationContext private val context: Context
){
    suspend operator fun invoke(uri: Uri, products: List<Product>): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val outputStream = context.contentResolver.openOutputStream(uri)
                ?: throw Exception("No se pudo abrir el flujo de escritura del archivo.")

            val writer = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(writer)
            val document = Document(pdfDocument)

            val title = Paragraph("REPORTE DE PRODUCTOS")
                .setBold()
                .setFontSize(18f)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20f)
            document.add(title)


            val columnWidths = floatArrayOf(2.5f, 1f, 1.5f, 2f, 3f)
            val table = Table(UnitValue.createPercentArray(columnWidths))
            table.setWidth(UnitValue.createPercentValue(100f))

            val headers = listOf("NOMBRE", "CANT.", "PRECIO", "TIENDA", "DETALLES")
            headers.forEach { headerText ->
                val headerCell = Cell()
                    .add(Paragraph(headerText).setBold().setFontSize(11f))
                    .setTextAlignment(TextAlignment.CENTER)
                table.addHeaderCell(headerCell)
            }

            products.forEach { product ->
                table.addCell(Cell().add(Paragraph(product.proName).setFontSize(10f)))
                table.addCell(
                    Cell().add(Paragraph(product.proAmount.toString()).setFontSize(10f))
                        .setTextAlignment(TextAlignment.CENTER)
                )
                table.addCell(
                    Cell().add(Paragraph(String.format("%.2f €", product.proPrice)).setFontSize(10f))
                        .setTextAlignment(TextAlignment.RIGHT)
                )
                table.addCell(Cell().add(Paragraph(product.proMarketName).setFontSize(10f)))

                val attributesText = buildString {
                    val validStatus = product.proStatus?.takeIf { it.isNotBlank() && it != "NONE" && it != "NINGUNO" }
                    if (validStatus != null) {
                        append("Estado: ${validStatus.uppercase()}\n")
                    }

                    product.dynamicAttributes.forEach { (key, value) ->
                        val cleanValue = value.toString().replace("\"", "")
                        append("${key.uppercase()}: $cleanValue\n")
                    }
                }.trim()

                val finalAttrText = if (attributesText.isEmpty()) "-" else attributesText
                table.addCell(Cell().add(Paragraph(finalAttrText).setFontSize(9f)))
            }

            document.add(table)
            document.close()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}