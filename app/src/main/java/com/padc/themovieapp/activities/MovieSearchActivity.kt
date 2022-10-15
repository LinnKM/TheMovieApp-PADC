package com.padc.themovieapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.widget.textChanges
import com.padc.themovieapp.R
import com.padc.themovieapp.adapters.MovieListAdapter
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity(), MovieViewHolderDelegate {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MovieSearchActivity::class.java)
            return intent
        }
    }

    private lateinit var mMovieAdapter: MovieListAdapter

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpListeners() {
        edtSearch.textChanges()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .flatMap { mMovieModel.searchMovie(it.toString()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            }, {
                showError(it.localizedMessage ?: "")
            })
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieListAdapter(this)
        rvMovies.adapter = mMovieAdapter
        rvMovies.layoutManager = GridLayoutManager(this, 2)
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }
}