package com.example.batch.connect

import java.time.LocalDateTime

data class Billing(
    val id: Int,
    val amount: Int,
    val name: String,
    val dateTime: LocalDateTime
)
