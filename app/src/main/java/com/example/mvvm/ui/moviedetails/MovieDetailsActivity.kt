package com.example.mvvm.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.data.api.MovieClient
import com.example.mvvm.data.api.MovieClient.IMAGE_POSTER
import com.example.mvvm.data.api.TheMovieDbInterface
import com.example.mvvm.data.repository.NetworkStatus
import com.example.mvvm.data.vo.MovieDetailsRepository
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {


    lateinit var movieDetailsRepository: MovieDetailsRepository
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_movie)

        val movieid = intent.getStringExtra("id")

        val apiService: TheMovieDbInterface = MovieClient.getClient()

        movieDetailsRepository = MovieDetailsRepository(apiService)


        val viewModelFactory = ScoreViewModelFactory(movieDetailsRepository, movieid)

        movieDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)

        movieDetailsViewModel.movieDetails.observe(this, Observer {

            movie_title.text=it.title
            movie_tagline.text=it.tagline
            movie_release_date.text=it.releaseDate
            movie_rating.text=it.voteAverage.toString()
            movie_runtime.text=it.runtime.toString()+" Minutes"
            movie_overview.text=it.overview


            val formatcurrencyConverter=NumberFormat.getCurrencyInstance(Locale.US)

            movie_budget.text=formatcurrencyConverter.format(it.budget)
            movie_revenue.text=formatcurrencyConverter.format(it.revenue)

            val posterPath=IMAGE_POSTER+it.posterPath

            Glide.with(this).load(posterPath).into(iv_movie_poster)

        })

        movieDetailsViewModel.networkstate.observe(this, Observer {
            progress_bar.visibility=if(it== NetworkStatus.Loading) View.VISIBLE else View.GONE

            txt_error.visibility=if(it== NetworkStatus.Error) View.VISIBLE else View.GONE
        })
    }


    class ScoreViewModelFactory(
        private val movieDetailsRepository: MovieDetailsRepository,
        private val movieid: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                return MovieDetailsViewModel(movieDetailsRepository, movieid) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}