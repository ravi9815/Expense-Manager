package com.example.easykhatabahi.models

class Category {
    var categoryName: String? = null
    var categoryImage = 0
    var categoryColor = 0

    constructor()
    constructor(categoryName: String?, categoryImage: Int, categoryColor: Int) {
        this.categoryName = categoryName
        this.categoryImage = categoryImage
        this.categoryColor = categoryColor
    }
}
