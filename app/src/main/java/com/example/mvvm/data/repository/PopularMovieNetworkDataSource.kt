package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mvvm.data.api.MovieClient.FIRSTPAGE
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularMovieNetworkDataSource(
    private val apiService: TheMovieDbInterface,
    private val compositediscposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    val networkstate = MutableLiveData<NetworkStatus>()
    val getNetworkState: LiveData<NetworkStatus>
        get() = networkstate


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkstate.postValue(NetworkStatus.Loading)

        compositediscposable.add(
            apiService.getPopularMovies(FIRSTPAGE).subscribeOn(Schedulers.io()).subscribe({

                callback.onResult(it.results,null, FIRSTPAGE+1)

                networkstate.postValue(NetworkStatus.Loaded)
            },
                {
                    networkstate.postValue(NetworkStatus.Error)
                })

        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkstate.postValue(NetworkStatus.Loading)

        compositediscposable.add(
            apiService.getPopularMovies(params.key).subscribeOn(Schedulers.io()).subscribe({

               if(it.totalPages>=params.key){
                   callback.onResult(it.results,params.key+1)
                   networkstate.postValue(NetworkStatus.Loaded)
               }else{
                   networkstate.postValue(NetworkStatus.Endoflist)
               }
            },
                {
                    networkstate.postValue(NetworkStatus.Error)
                })

        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}