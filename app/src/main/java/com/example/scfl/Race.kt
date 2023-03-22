package com.example.scfl

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Race (
    var name: String = "Name",
    var number: Int = 0,
    var results: MutableList<String> = mutableListOf(),
    var date: String = LocalDate.now().toString()

)
