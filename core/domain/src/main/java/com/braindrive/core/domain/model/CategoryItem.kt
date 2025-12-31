package com.braindrive.core.domain.model

data class CategoryItem(
    val id: String,
    val name: String,
    val category: ItemCategory
)

enum class ItemCategory {
    EDIBLE,
    CONSUMER,
    HUMAN
}

