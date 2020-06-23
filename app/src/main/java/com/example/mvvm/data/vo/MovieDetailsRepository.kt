package com.example.mvvm.data.vo

import androidx.lifecycle.LiveData
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.repository.MovieDetailsNetworkDataSource
import com.example.mvvm.data.repository.NetworkStatus
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val dataservice: TheMovieDbInterface) {

    lateinit var movieDetailsNetwprkDataSource: MovieDetailsNetworkDataSource


    fun fetchMovieDetails(compositeDisposable: CompositeDisposable, movie_id: String): LiveData<MovieDetails> {

        movieDetailsNetwprkDataSource = MovieDetailsNetworkDataSource(dataservice, compositeDisposable)


        movieDetailsNetwprkDataSource.fetchMovieDetails(movie_id)

        return movieDetailsNetwprkDataSource.getdownloadedmoviedetails
    }

    fun fetchNetworkState(): LiveData<NetworkStatus> {

        return movieDetailsNetwprkDataSource.getNetworkState

    }


}