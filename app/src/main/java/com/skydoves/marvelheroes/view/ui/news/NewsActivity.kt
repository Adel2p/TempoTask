package com.skydoves.marvelheroes.view.ui.news

import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.marvelheroes.R
import com.skydoves.marvelheroes.databinding.ActivityMainBinding
import com.skydoves.marvelheroes.databinding.ActivityNewsBinding
import com.skydoves.marvelheroes.view.adapter.ArticleAdapter
import com.skydoves.marvelheroes.view.adapter.PosterAdapter
import com.skydoves.transformationlayout.onTransformationStartContainer
import org.koin.android.viewmodel.ext.android.getViewModel

class NewsActivity: BindingActivity<ActivityNewsBinding>(R.layout.activity_news) {
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@NewsActivity
            adapter = ArticleAdapter()
           vm = getViewModel()
        }
    }
}