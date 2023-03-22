package com.example.scfl

import kotlinx.serialization.Serializable

@Serializable
data class Rider (
    var name: String = "Name",
    var number: String = "",
    var lastrace: String = "-",
    var standing: String = " "
)
