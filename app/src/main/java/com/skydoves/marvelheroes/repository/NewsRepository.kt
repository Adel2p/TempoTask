package com.skydoves.marvelheroes.repository

import androidx.lifecycle.MutableLiveData
import com.skydoves.marvelheroes.mapper.ErrorResponseMapper
import com.skydoves.marvelheroes.model.Articles
import com.skydoves.marvelheroes.model.BaseResponse
import com.skydoves.marvelheroes.model.Poster
import com.skydoves.marvelheroes.model.PosterErrorResponse
import com.skydoves.marvelheroes.network.MarvelClient
import com.skydoves.marvelheroes.persistence.PosterDao
import com.skydoves.marvelheroes.repository.Repository
import com.skydoves.sandwich.*
import com.skydoves.sandwich.disposables.CompositeDisposable
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsRepository constructor(
    private val marvelClient: MarvelClient,
    private val marvelDataSource: ResponseDataSource<BaseResponse>,
    private val posterDao: PosterDao
) : Repository {

    init {
        Timber.d("Injection MainRepository")
    }
    suspend fun loadMarvelNews(
        disposable: CompositeDisposable,
        error: (String?) -> Unit
    ) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Articles>>()
        var articles :List<Articles>? = null


        /**
             * fetch [Poster] from the network and getting [ApiResponse] asynchronously.
             * @see [onSuccess](https://github.com/skydoves/sandwich#onsuccess-onerror-onexception)
             * */
            /**
             * fetch [Poster] from the network and getting [ApiResponse] asynchronously.
             * @see [onSuccess](https://github.com/skydoves/sandwich#onsuccess-onerror-onexception)
             * */
            marvelClient.fetchNews(marvelDataSource, disposable) { apiResponse ->
                apiResponse
                    // handle the case when the API request gets a success response.
                    .onSuccess {
                        data.whatIfNotNull {
                            liveData.postValue(it.articles)
                            articles=it.articles
                          //  posterDao.insertArticleList(it)
                        }
                    }
                    // handle the case when the API request gets an error response.
                    // e.g. internal server error.
                    .onError {
                        /** maps the [ApiResponse.Failure.Error] to the [PosterErrorResponse] using the mapper. */
                        /** maps the [ApiResponse.Failure.Error] to the [PosterErrorResponse] using the mapper. */
                        map(ErrorResponseMapper) { error("[Code: $code]: $message") }
                    }
                    // handle the case when the API request gets an exception response.
                    // e.g., network connection error.
                    .onException { error(message()) }
            }

        liveData.apply { postValue(articles) }
    }
}