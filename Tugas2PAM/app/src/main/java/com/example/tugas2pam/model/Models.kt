package com.example.tugas2pam.model

enum class Cat { TECH, SPORT, BUSINESS, HEALTH }

data class News(
    val id: String,
    val t: String,
    val c: Cat,
    val time: Long
)

data class Detail(
    val id: String,
    val title: String,
    val content: String
)

data class NewsUi(
    val id: String,
    val a: String,
    val b: String
)
