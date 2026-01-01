package com.braindrive.core.domain.model

data class CategorizeQuestion(
    val item: CategoryItem,
    val correctCategory: ItemCategory,
    val options: List<ItemCategory>
) {
    fun isCorrect(selectedCategory: ItemCategory): Boolean {
        return selectedCategory == correctCategory
    }
}

