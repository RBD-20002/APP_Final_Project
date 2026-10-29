package com.example.finalproject.page.product.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.component.AttributeType
import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.remote.dto.product.ProductCreateDTO
import com.example.finalproject.data.remote.dto.product.ProductUpdateDTO
import com.example.finalproject.data.repository.product.ProductRepository
import com.example.finalproject.model.DynamicAttributeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

@HiltViewModel
class ProductFormViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProductFormUiEvent) {
        when (event) {
            is ProductFormUiEvent.InitForm -> {
                if (event.idProduct != null) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true, productId = event.idProduct, idCategory = event.idCategory, idUser = event.idUser) }
                        val result = productRepository.getProductByUser()

                        result.onSuccess { list ->
                            val p = list.find { it.idProduct == event.idProduct }
                            if (p != null) {
                                val attributes = p.dynamicAttributes.map { (key, value) ->
                                    val strVal = value.toString().replace("\"", "")
                                    val type = when {
                                        strVal in listOf("NONE", "SAVED", "BORROWED", "GUARDADO", "PRESTADA", "PRESTADO", "NINGUNO") -> AttributeType.STATUS
                                        strVal.matches(Regex("""\d{1,2}/\d{1,2}/\d{4}""")) -> AttributeType.DATE
                                        else -> AttributeType.TEXT
                                    }
                                    DynamicAttributeItem(key = key, value = strVal, type = type)
                                }
                                _uiState.update {
                                    it.copy(
                                        name = p.proName,
                                        amount = p.proAmount.toString(),
                                        price = p.proPrice.toString(),
                                        marketName = p.proMarketName,
                                        status = p.proStatus,
                                        imageUrl = p.imageUrl,
                                        dynamicAttributes = attributes,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                        result.onFailure { e ->
                            _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al cargar el producto") }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        val currentUserId = sessionDataStore.getUserId() ?: ""
                        _uiState.update {
                            it.copy(
                                idCategory = event.idCategory,
                                idUser = if (event.idUser.isBlank()) currentUserId else event.idUser,
                                status = null
                            )
                        }
                    }
                }
            }
            is ProductFormUiEvent.OnNameChanged -> {
                _uiState.update { it.copy(name = event.name, errorMessage = null) }
            }
            is ProductFormUiEvent.OnAmountChanged -> {
                if(event.amount.isEmpty() || event.amount.all { it.isDigit() }) {
                    _uiState.update { it.copy(amount = event.amount, errorMessage = null) }
                }
            }
            is ProductFormUiEvent.OnPriceChanged -> {
                if(event.price.isEmpty() || event.price.matches(Regex("""^\d*\.?\d*$"""))) {
                    _uiState.update { it.copy(price = event.price, errorMessage = null) }
                }
            }
            is ProductFormUiEvent.OnMarketNameChanged -> {
                _uiState.update { it.copy(marketName = event.marketName, errorMessage = null) }
            }
            is ProductFormUiEvent.OnStatusChanged -> {
                _uiState.update { it.copy(status = event.status) }
            }
            is ProductFormUiEvent.OnAddAttribute -> {
                val current = _uiState.value.dynamicAttributes.toMutableList()

                if ((event.type == AttributeType.DATE || event.type == AttributeType.STATUS) && current.any { it.type == event.type }) {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Solo puedes añadir un atributo de este tipo.",
                            isTitleDialogOpen = false,
                            isAttributeDialogOpen = false,
                            selectedAttributeTypeForDialog = null
                        )
                    }
                } else {
                    val initialValue = if (event.type == AttributeType.STATUS) "NINGUNO" else ""
                    current.add(DynamicAttributeItem(key = event.key, value = initialValue, type = event.type))
                    _uiState.update {
                        it.copy(
                            dynamicAttributes = current,
                            isTitleDialogOpen = false,
                            isAttributeDialogOpen = false,
                            selectedAttributeTypeForDialog = null,
                            errorMessage = null
                        )
                    }
                }
            }
            is ProductFormUiEvent.OnUpdateAttributeValue -> {
                val current = _uiState.value.dynamicAttributes.toMutableList()
                if(event.index in current.indices) {
                    current[event.index] = current[event.index].copy(value = event.value)
                    _uiState.update { it.copy(dynamicAttributes = current) }
                }
            }
            is ProductFormUiEvent.OnRemoveAttribute -> {
                val current = _uiState.value.dynamicAttributes.toMutableList()
                if(event.index in current.indices) {
                    current.removeAt(event.index)
                    _uiState.update { it.copy(dynamicAttributes = current) }
                }
            }
            ProductFormUiEvent.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
            ProductFormUiEvent.OnSaveClick -> {
                saveProduct()
            }
            ProductFormUiEvent.OnOpenAttributeDialog -> {
                _uiState.update { it.copy(isAttributeDialogOpen = true) }
            }
            ProductFormUiEvent.OnDismissAttributeDialog -> {
                _uiState.update { it.copy(isAttributeDialogOpen = false) }
            }
            is ProductFormUiEvent.OnOpenTitleDialog -> {
                _uiState.update {
                    it.copy(
                        isTitleDialogOpen = true,
                        selectedAttributeTypeForDialog = event.type
                    )
                }
            }
            ProductFormUiEvent.OnDismissTitleDialog -> {
                _uiState.update {
                    it.copy(
                        isTitleDialogOpen = false,
                        selectedAttributeTypeForDialog = null
                    )
                }
            }
            is ProductFormUiEvent.OnImageChanged -> {
                _uiState.update { it.copy(imageUrl = event.imageUrl) }
            }
        }
    }

    private fun saveProduct() {
        val state = _uiState.value
        val name = state.name.trim()
        val amount = state.amount.toIntOrNull()
        val price = state.price.toDoubleOrNull()
        val marketName = state.marketName.trim()

        if (state.idCategory.isBlank() || state.idUser.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Error: Faltan los datos de categoría o usuario.")
            }
            return
        }

        if (name.isBlank() || amount == null || price == null || marketName.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Completa nombre, cantidad, precio y mercado con valores válidos.")
            }
            return
        }

        val finalStatus = if (state.status.isNullOrBlank() || state.status == "NINGUNO") "NONE" else state.status

        val dynamicMap = state.dynamicAttributes
            .filter { it.key.isNotBlank() }
            .associate { it.key to JsonPrimitive(it.value) }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            if (state.isEditing) {
                val updateDTO = ProductUpdateDTO(
                    idProduct = state.productId!!,
                    proName = name,
                    proAmount = amount,
                    proPrice = price,
                    proMarketName = marketName,
                    proStatus = finalStatus ?: "NONE",
                    imageUrl = state.imageUrl ?: "",
                    idCategory = state.idCategory,
                    idUser = state.idUser,
                    dynamicAttributes = dynamicMap
                )

                val result = productRepository.updateProduct(updateDTO)

                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                result.onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al actualizar el producto")
                    }
                }

            } else {
                val createDTO = ProductCreateDTO(
                    proName = name,
                    proAmount = amount,
                    proPrice = price,
                    proMarketName = marketName,
                    proStatus = finalStatus ?: "NONE",
                    imageUrl = state.imageUrl ?: "",
                    idCategory = state.idCategory,
                    idUser = state.idUser,
                    dynamicAttributes = dynamicMap
                )

                val result = productRepository.createProduct(createDTO)

                result.onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                result.onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al crear el producto")
                    }
                }
            }
        }
    }
}