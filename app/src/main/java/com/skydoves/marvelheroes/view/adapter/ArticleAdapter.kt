package com.skydoves.marvelheroes.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import com.skydoves.marvelheroes.R
import com.skydoves.marvelheroes.databinding.ItemArticleBinding
import com.skydoves.marvelheroes.model.Articles
import com.skydoves.marvelheroes.view.ui.news.NewsDetailsActivity

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val items = mutableListOf<Articles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = parent.binding<ItemArticleBinding>(R.layout.item_article)
        return ArticleViewHolder(binding).apply {
            binding.root.setOnClickListener { view ->
                val position =
                    adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                NewsDetailsActivity.startActivity(
                    view.context,
                    binding.transformationLayout,
                    items[position]
                   )
            }
        }
    }

    fun updatePosterList(posters: List<Articles>) {
        items.clear()
        items.addAll(posters)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.apply {
            poster = items[position]
            veil = itemVeilLayout
            executePendingBindings()
        }
    }

    fun getPoster(index: Int): Articles = items[index]

    class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}