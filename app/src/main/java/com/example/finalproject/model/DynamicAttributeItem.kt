package com.example.finalproject.model

import com.example.finalproject.component.AttributeType
import java.util.UUID

data class DynamicAttributeItem (
    val id: String = UUID.randomUUID().toString(),
    val key: String,
    val value: String,
    val type: AttributeType = AttributeType.TEXT
)