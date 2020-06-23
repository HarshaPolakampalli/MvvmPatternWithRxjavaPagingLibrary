package com.oxcoding.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mvvm.data.repository.NetworkStatus
import com.example.mvvm.data.vo.Movie
import com.example.mvvm.data.vo.PopularMoviesRepository
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesViewModel(private val movieRepository : PopularMoviesRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchPopularMoviesPagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkStatus> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}