package com.example.mvvm.data.api

import com.example.mvvm.data.vo.MovieDetails
import com.example.mvvm.data.vo.PopularMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {


    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page:Int): Single<PopularMovies>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:String): Single<MovieDetails>
}