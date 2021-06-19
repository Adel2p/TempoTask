package com.skydoves.marvelheroes.view.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.marvelheroes.R
import com.skydoves.marvelheroes.databinding.ActivityNewsDetailBinding
import com.skydoves.marvelheroes.extensions.onTransformationEndContainerApplyParams
import com.skydoves.marvelheroes.model.Articles
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.whatif.whatIfNotNullOrEmpty

class NewsDetailsActivity :
    BindingActivity<ActivityNewsDetailBinding>(R.layout.activity_news_detail) {
    private val article: Articles by bundleNonNull(ARTICLE_ITEM)
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams()
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@NewsDetailsActivity
        }
        Glide.with(this)
            .load(article.urlToImage).into(binding.articleImage)
        binding.itemArticleTitle.text = article.title
        article.description.whatIfNotNullOrEmpty {
            binding.itemArticleDesc.text = article.content
        }
        article.author.whatIfNotNullOrEmpty {
            binding.itemArticleAuthor.text = "by ${article.author}"
        }
        binding.itemArticleSource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            if (article != null) {
                intent.data = Uri.parse(article.url)
            }
            startActivity(intent)
        }
    }

    companion object {
        private const val ARTICLE_ITEM = "ARTICLE_ITEM"
        fun startActivity(
            context: Context,
            transformationLayout: TransformationLayout,
            article: Articles
        ) = context.intentOf<NewsDetailsActivity> {
            putExtra(ARTICLE_ITEM to article)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

}