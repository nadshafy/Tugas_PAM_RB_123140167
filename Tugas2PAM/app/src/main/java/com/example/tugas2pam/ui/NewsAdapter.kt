package com.example.tugas2pam.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas2pam.R
import com.example.tugas2pam.model.NewsUi

class NewsAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<NewsAdapter.H>() {

    private var data: List<NewsUi> = emptyList()

    fun setData(x: List<NewsUi>) {
        data = x
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return H(v)
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        val item = data[position]
        holder.t1.text = item.a
        holder.t2.text = item.b
        holder.itemView.setOnClickListener { onClick(item.id) }
    }

    override fun getItemCount(): Int = data.size

    class H(v: View) : RecyclerView.ViewHolder(v) {
        val t1: TextView = v.findViewById(R.id.tvTitle)
        val t2: TextView = v.findViewById(R.id.tvMeta)
    }
}
