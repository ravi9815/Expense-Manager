package com.example.easykhatabahi.utils

import com.example.easykhatabahi.R
import com.example.easykhatabahi.models.Category

object Constants {
    var INCOME = "INCOME"
    var EXPENSE = "EXPENSE"
    var categories: ArrayList<Category>? = null
    var DAILY = 0
    var MONTHLY = 1
    var CALENDAR = 2
    var SUMMARY = 3
    var NOTES = 4
    var SELECTED_TAB = 0
    var SELECTED_TAB_STATS = 0
    var SELECTED_STATS_TYPE = INCOME
    fun setCategories() {
        categories = ArrayList()
        categories!!.add(Category("Salary", R.drawable.ic_salary, R.color.category1))
        categories!!.add(Category("Business", R.drawable.ic_business, R.color.category2))
        categories!!.add(Category("Investment", R.drawable.ic_investment, R.color.category3))
        categories!!.add(Category("Loan", R.drawable.ic_loan, R.color.category4))
        categories!!.add(Category("Rent", R.drawable.ic_rent, R.color.category5))
        categories!!.add(Category("Other", R.drawable.ic_other, R.color.category6))
    }

    fun getCategoryDetails(categoryName: String): Category? {
        for (cat in categories!!) {
            if (cat.categoryName == categoryName) {
                return cat
            }
        }
        return null
    }

    fun getAccountsColor(accountName: String?): Int {
        return when (accountName) {
            "Bank" -> R.color.bank_color
            "Cash" -> R.color.cash_color
            "Card" -> R.color.card_color
            else -> R.color.default_color
        }
    }
}
