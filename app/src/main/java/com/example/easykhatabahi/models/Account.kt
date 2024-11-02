package com.example.easykhatabahi.models

class Account {
    var accountAmount = 0.0
    var accountName: String? = null

    constructor()
    constructor(accountAmount: Double, accountName: String?) {
        this.accountAmount = accountAmount
        this.accountName = accountName
    }
}
