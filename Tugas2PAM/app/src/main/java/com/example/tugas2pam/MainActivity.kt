package com.example.tugas2pam

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas2pam.model.Cat
import com.example.tugas2pam.ui.NewsAdapter
import com.example.tugas2pam.ui.NewsVM
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vm: NewsVM by viewModels()
    private var dlg: AlertDialog? = null
    private var lastShown: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvRead = findViewById<TextView>(R.id.tvRead)
        tvRead.text = "Read: 0"

        val btnAll = findViewById<Button>(R.id.btnAll)
        val btnTech = findViewById<Button>(R.id.btnTech)
        val btnSport = findViewById<Button>(R.id.btnSport)
        val btnBusiness = findViewById<Button>(R.id.btnBusiness)
        val btnHealth = findViewById<Button>(R.id.btnHealth)

        fun setActive(active: Button) {
            val all = listOf(btnAll, btnTech, btnSport, btnBusiness, btnHealth)
            all.forEach { it.isSelected = false }
            active.isSelected = true
        }

        setActive(btnAll)

        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        val adp = NewsAdapter { id -> vm.click(id) }
        rv.adapter = adp

        btnAll.setOnClickListener { vm.setCat(null); setActive(btnAll) }
        btnTech.setOnClickListener { vm.setCat(Cat.TECH); setActive(btnTech) }
        btnSport.setOnClickListener { vm.setCat(Cat.SPORT); setActive(btnSport) }
        btnBusiness.setOnClickListener { vm.setCat(Cat.BUSINESS); setActive(btnBusiness) }
        btnHealth.setOnClickListener { vm.setCat(Cat.HEALTH); setActive(btnHealth) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch { vm.ui.collect { list -> adp.setData(list) } }

                launch { vm.read.collect { x -> tvRead.text = "Read: $x" } }

                launch {
                    vm.d.collect { d ->
                        if (d != null) {
                            val key = d.id + "|" + d.title
                            if (lastShown == key) return@collect
                            lastShown = key

                            dlg?.dismiss()
                            dlg = AlertDialog.Builder(this@MainActivity)
                                .setTitle(d.title)
                                .setMessage(d.content)
                                .setPositiveButton("Tutup") { dialog, _ -> dialog.dismiss() }
                                .create()

                            dlg?.show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        dlg?.dismiss()
        super.onDestroy()
    }
}
