package com.example.mvvm.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.data.repository.NetworkStatus
import com.example.mvvm.data.vo.MovieDetails
import com.example.mvvm.data.vo.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(movieDetailsRepository: MovieDetailsRepository, movie_id: String) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchMovieDetails(compositeDisposable, movie_id)
    }

    val networkstate: LiveData<NetworkStatus> by lazy {
        movieDetailsRepository.fetchNetworkState()
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}