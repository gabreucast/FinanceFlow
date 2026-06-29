package com.example.financeflow.entity

data class Transaction(
    val id: Long,
    val description: String,
    val amount: Double,
    val date: String,
    val type: String
)