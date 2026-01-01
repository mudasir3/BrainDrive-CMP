package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.CategorizeQuestion
import com.braindrive.core.domain.model.CategoryItem
import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.ItemCategory
import javax.inject.Inject

class GenerateCategorizeQuestionUseCase @Inject constructor() {
    
    private val edibleItems = listOf(
        CategoryItem("apple", "Apple", ItemCategory.EDIBLE),
        CategoryItem("banana", "Banana", ItemCategory.EDIBLE),
        CategoryItem("bread", "Bread", ItemCategory.EDIBLE),
        CategoryItem("cheese", "Cheese", ItemCategory.EDIBLE),
        CategoryItem("chicken", "Chicken", ItemCategory.EDIBLE),
        CategoryItem("chocolate", "Chocolate", ItemCategory.EDIBLE),
        CategoryItem("egg", "Egg", ItemCategory.EDIBLE),
        CategoryItem("fish", "Fish", ItemCategory.EDIBLE),
        CategoryItem("honey", "Honey", ItemCategory.EDIBLE),
        CategoryItem("milk", "Milk", ItemCategory.EDIBLE),
        CategoryItem("orange", "Orange", ItemCategory.EDIBLE),
        CategoryItem("pizza", "Pizza", ItemCategory.EDIBLE),
        CategoryItem("rice", "Rice", ItemCategory.EDIBLE),
        CategoryItem("salad", "Salad", ItemCategory.EDIBLE),
        CategoryItem("sandwich", "Sandwich", ItemCategory.EDIBLE),
        CategoryItem("soup", "Soup", ItemCategory.EDIBLE),
        CategoryItem("steak", "Steak", ItemCategory.EDIBLE),
        CategoryItem("sushi", "Sushi", ItemCategory.EDIBLE),
        CategoryItem("tomato", "Tomato", ItemCategory.EDIBLE),
        CategoryItem("yogurt", "Yogurt", ItemCategory.EDIBLE)
    )
    
    private val consumerItems = listOf(
        CategoryItem("book", "Book", ItemCategory.CONSUMER),
        CategoryItem("car", "Car", ItemCategory.CONSUMER),
        CategoryItem("chair", "Chair", ItemCategory.CONSUMER),
        CategoryItem("computer", "Computer", ItemCategory.CONSUMER),
        CategoryItem("laptop", "Laptop", ItemCategory.CONSUMER),
        CategoryItem("phone", "Phone", ItemCategory.CONSUMER),
        CategoryItem("table", "Table", ItemCategory.CONSUMER),
        CategoryItem("television", "Television", ItemCategory.CONSUMER),
        CategoryItem("bicycle", "Bicycle", ItemCategory.CONSUMER),
        CategoryItem("watch", "Watch", ItemCategory.CONSUMER),
        CategoryItem("camera", "Camera", ItemCategory.CONSUMER),
        CategoryItem("headphones", "Headphones", ItemCategory.CONSUMER),
        CategoryItem("keyboard", "Keyboard", ItemCategory.CONSUMER),
        CategoryItem("mouse", "Mouse", ItemCategory.CONSUMER),
        CategoryItem("speaker", "Speaker", ItemCategory.CONSUMER),
        CategoryItem("tablet", "Tablet", ItemCategory.CONSUMER),
        CategoryItem("refrigerator", "Refrigerator", ItemCategory.CONSUMER),
        CategoryItem("microwave", "Microwave", ItemCategory.CONSUMER),
        CategoryItem("washing_machine", "Washing Machine", ItemCategory.CONSUMER),
        CategoryItem("vacuum", "Vacuum", ItemCategory.CONSUMER)
    )
    
    private val humanItems = listOf(
        CategoryItem("doctor", "Doctor", ItemCategory.HUMAN),
        CategoryItem("teacher", "Teacher", ItemCategory.HUMAN),
        CategoryItem("engineer", "Engineer", ItemCategory.HUMAN),
        CategoryItem("nurse", "Nurse", ItemCategory.HUMAN),
        CategoryItem("chef", "Chef", ItemCategory.HUMAN),
        CategoryItem("pilot", "Pilot", ItemCategory.HUMAN),
        CategoryItem("lawyer", "Lawyer", ItemCategory.HUMAN),
        CategoryItem("artist", "Artist", ItemCategory.HUMAN),
        CategoryItem("musician", "Musician", ItemCategory.HUMAN),
        CategoryItem("scientist", "Scientist", ItemCategory.HUMAN),
        CategoryItem("athlete", "Athlete", ItemCategory.HUMAN),
        CategoryItem("firefighter", "Firefighter", ItemCategory.HUMAN),
        CategoryItem("police", "Police Officer", ItemCategory.HUMAN),
        CategoryItem("farmer", "Farmer", ItemCategory.HUMAN),
        CategoryItem("builder", "Builder", ItemCategory.HUMAN),
        CategoryItem("designer", "Designer", ItemCategory.HUMAN),
        CategoryItem("writer", "Writer", ItemCategory.HUMAN),
        CategoryItem("photographer", "Photographer", ItemCategory.HUMAN),
        CategoryItem("dentist", "Dentist", ItemCategory.HUMAN),
        CategoryItem("veterinarian", "Veterinarian", ItemCategory.HUMAN)
    )
    
    operator fun invoke(category: ItemCategory, difficulty: Difficulty = Difficulty.MEDIUM): CategorizeQuestion {
        val items = when (category) {
            ItemCategory.EDIBLE -> edibleItems
            ItemCategory.CONSUMER -> consumerItems
            ItemCategory.HUMAN -> humanItems
        }
        
        val selectedItem = items.random()
        val allCategories = ItemCategory.values().toList()
        val wrongOptions = allCategories.filter { it != selectedItem.category }.shuffled()
        
        // For easy: 2 options, medium: 3 options, hard: all 3 options
        val numOptions = when (difficulty) {
            Difficulty.EASY -> 2
            Difficulty.MEDIUM -> 3
            Difficulty.HARD -> 3
        }
        
        val options = (listOf(selectedItem.category) + wrongOptions.take(numOptions - 1)).shuffled()
        
        return CategorizeQuestion(
            item = selectedItem,
            correctCategory = selectedItem.category,
            options = options
        )
    }
}

