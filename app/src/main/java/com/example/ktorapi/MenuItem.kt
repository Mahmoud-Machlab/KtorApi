package com.example.ktorapi

import kotlinx.serialization.Serializable

@Serializable

data class MenuItem(val name: String, val price: Double)

