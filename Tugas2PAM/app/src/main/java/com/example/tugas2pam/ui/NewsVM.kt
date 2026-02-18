package com.example.tugas2pam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas2pam.data.NewsRepo
import com.example.tugas2pam.model.Cat
import com.example.tugas2pam.model.Detail
import com.example.tugas2pam.model.News
import com.example.tugas2pam.model.NewsUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsVM : ViewModel() {

    private val r = NewsRepo()

    private val _cat = MutableStateFlow<Cat?>(null)
    val cat: StateFlow<Cat?> = _cat.asStateFlow()

    private val _read = MutableStateFlow(0)
    val read: StateFlow<Int> = _read.asStateFlow()

    private val _list = MutableStateFlow<List<News>>(emptyList())
    private val list = _list.asStateFlow()

    private val _d = MutableStateFlow<Detail?>(null)
    val d: StateFlow<Detail?> = _d.asStateFlow()

    val ui: StateFlow<List<NewsUi>> =
        combine(list, cat) { a, b ->
            val x = if (b == null) a else a.filter { it.c == b }
            x.map {
                NewsUi(
                    it.id,
                    "📰 " + it.t,
                    it.c.name + " • " + SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(it.time))
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            r.stream().collect { n ->
                _list.update { old -> (listOf(n) + old).take(50) }
            }
        }
    }

    fun setCat(c: Cat?) {
        _cat.value = c
    }

    fun click(id: String) {
        _read.update { it + 1 }

        val found = _list.value.firstOrNull { it.id == id }
        val title = found?.t ?: "Judul Tidak Diketahui"

        viewModelScope.launch(Dispatchers.IO) {
            val dd = async { r.detail(id, title) }
            _d.value = dd.await()
        }
    }
}
