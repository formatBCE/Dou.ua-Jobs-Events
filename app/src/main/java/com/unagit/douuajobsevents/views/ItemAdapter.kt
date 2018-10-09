package com.unagit.douuajobsevents.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unagit.douuajobsevents.R
import com.unagit.douuajobsevents.data.Item
import kotlinx.android.synthetic.main.list_item.view.*

class ItemAdapter(private var items: List<Item>, private val listener: Listener)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            itemView.item_title.text = item.title
            itemView.item_link.text = item.link

        }
    }
    interface Listener {

    }
}