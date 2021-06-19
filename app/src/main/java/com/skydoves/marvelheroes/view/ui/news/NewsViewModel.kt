package com.skydoves.marvelheroes.view.ui.news

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import com.skydoves.bindables.bindingProperty
import com.skydoves.marvelheroes.base.LiveCoroutinesViewModel
import com.skydoves.marvelheroes.model.Articles
import com.skydoves.marvelheroes.repository.NewsRepository
import timber.log.Timber

class NewsViewModel constructor(
    private val newsRepository: NewsRepository
) : LiveCoroutinesViewModel() {
    val newsListLiveData: LiveData<List<Articles>>

    @get:Bindable
    var toast: String? by bindingProperty(null)
        private set

    init {
        Timber.d("injection MainViewModel")

        newsListLiveData = launchOnViewModelScope {
            newsRepository.loadMarvelNews(disposables) { toast = it }
        }
    }
}