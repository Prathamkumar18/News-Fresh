package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsAdapter(val listener:newsItemClicked):RecyclerView.Adapter<newsViewHolder>() {
    private val items:ArrayList<News> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=newsViewHolder(view)
        view.setOnClickListener{
            listener.onitemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.title.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title=itemView.findViewById<TextView>(R.id.Title)
    val image=itemView.findViewById<ImageView>(R.id.iv1)
    val author=itemView.findViewById<TextView>(R.id.Author)
}

interface newsItemClicked{
    fun onitemClicked(item:News)
}