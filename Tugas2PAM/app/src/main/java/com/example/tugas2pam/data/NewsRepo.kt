package com.example.tugas2pam.data

import com.example.tugas2pam.model.Cat
import com.example.tugas2pam.model.Detail
import com.example.tugas2pam.model.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepo {

    private val judul = listOf(
        "Kotlin Update", "Android Baru", "Match Seru", "Bisnis Naik", "Olahraga Hari Ini", "Sehat Tips"
    )

    fun stream(): Flow<News> = flow {
        var i = 1
        while (true) {
            delay(2000)
            val cc = Cat.entries.random()
            val tt = judul.random()
            emit(News("N$i", tt, cc, System.currentTimeMillis()))
            i++
        }
    }

    suspend fun detail(id: String, title: String): Detail {
        delay(400)
        val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."

        return Detail(
            id = id,
            title = title,
            content = lorem
        )
    }
}
