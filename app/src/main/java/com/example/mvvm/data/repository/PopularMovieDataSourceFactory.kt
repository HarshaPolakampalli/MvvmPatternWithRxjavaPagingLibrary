package com.example.mvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class PopularMovieDataSourceFactory(
    private val apiService: TheMovieDbInterface,
    private val compositediscposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val mutablelivedatasource = MutableLiveData<PopularMovieNetworkDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val popularMovieDataSource = PopularMovieNetworkDataSource(apiService, compositediscposable)

        mutablelivedatasource.postValue(popularMovieDataSource)

        return popularMovieDataSource
    }
}