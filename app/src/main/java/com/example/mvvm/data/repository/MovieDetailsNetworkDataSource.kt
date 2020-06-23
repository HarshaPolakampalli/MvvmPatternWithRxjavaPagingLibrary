package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkDataSource(private val apiService: TheMovieDbInterface,private val compositediscposable:CompositeDisposable){


    private val networkstate= MutableLiveData<NetworkStatus>()
    val getNetworkState: LiveData<NetworkStatus>
            get()=networkstate


    private val downloadedMovieDetails= MutableLiveData<MovieDetails>()
    val getdownloadedmoviedetails: LiveData<MovieDetails>
        get()=downloadedMovieDetails


    fun fetchMovieDetails(movie_id:String){

        networkstate.postValue(NetworkStatus.Loading)


        try{
            compositediscposable.add(apiService.getMovieDetails(movie_id)
                .subscribeOn(Schedulers.io()).subscribe({
                    downloadedMovieDetails.postValue(it)
                    networkstate.postValue(NetworkStatus.Loaded)
                },{
                    networkstate.postValue(NetworkStatus.Error)
                }))

        }catch (e:Exception){

        }


    }


}