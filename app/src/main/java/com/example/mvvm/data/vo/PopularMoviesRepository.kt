package com.example.mvvm.data.vo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvm.data.api.MovieClient.NOOFPOSTPERPAGE
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.repository.NetworkStatus
import com.example.mvvm.data.repository.PopularMovieDataSourceFactory
import com.example.mvvm.data.repository.PopularMovieNetworkDataSource
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesRepository(private val dataservice: TheMovieDbInterface) {


    lateinit var popularMovieDataSourceFactory: PopularMovieDataSourceFactory

    lateinit var moviepagedlist:LiveData<PagedList<Movie>>

    fun fetchPopularMoviesPagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {

        popularMovieDataSourceFactory= PopularMovieDataSourceFactory(dataservice,compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(NOOFPOSTPERPAGE)
            .build()
        moviepagedlist = LivePagedListBuilder(popularMovieDataSourceFactory, config).build()
        return moviepagedlist
    }

    fun getNetworkState(): LiveData<NetworkStatus> {
        return Transformations.switchMap<PopularMovieNetworkDataSource, NetworkStatus>(
            popularMovieDataSourceFactory.mutablelivedatasource, PopularMovieNetworkDataSource::networkstate)
    }


}