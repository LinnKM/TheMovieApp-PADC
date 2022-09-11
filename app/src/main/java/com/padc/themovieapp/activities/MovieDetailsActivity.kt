package com.padc.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Movie
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.padc.themovieapp.R
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.data.vos.TrailerVO
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import com.padc.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
                .putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    // ViewPods
    lateinit var mActorViewPod: ActorListViewPod
    lateinit var mCreatorViewPod: ActorListViewPod

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // Trailer List
    private var mTrailerList: List<TrailerVO>? = null
    var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setUpViewPods()
        setUpListeners()

        // Assign MovieId
        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let { id ->
            requestData(id)
        }
    }

    private fun setUpListeners() {

        // Back Button Listener
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

        // Play Trailer Listener
        btnPlayTrailer.setOnClickListener {
            mTrailerList?.let {
                val intent =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=${it[0].key}")
                    )
                startActivity(intent)
            }
        }

        // Favorite Listener
        ivFavorite.setOnClickListener {
            if (!isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_24dp)
                Toast.makeText(this, "Favorite Added", Toast.LENGTH_SHORT).show()
                isFavorite = true
            } else {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white_24dp)
                Toast.makeText(this, "Favorite Removed", Toast.LENGTH_SHORT).show()

                isFavorite = false
            }
        }
    }

    private fun setUpViewPods() {
        // Actor ViewPod
        mActorViewPod = vpActors as ActorListViewPod
        mActorViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = "",
        )
        // Creator ViewPod
        mCreatorViewPod = vpCreators as ActorListViewPod
        mCreatorViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators),
        )
    }

    // Request Data
    private fun requestData(movieId: Int) {
        mMovieModel.getMovieDetails(
            movieId = movieId.toString(),
            onSuccess = {
                bindData(movie = it)
            },
            onFailure = {

            }
        )

        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                mActorViewPod.setData(it.first)
                mCreatorViewPod.setData(it.second)
            },
            onFailure = {

            }
        )

        mMovieModel.getMovieTrailers(
            movieId = movieId.toString(),
            onSuccess = { trailerList ->
                mTrailerList = trailerList
            },
            onFailure = {

            }
        )
    }

    // Bind Data
    private fun bindData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMoviePoster)

        tvDetailMovieName.text = movie.originalTitle ?: ""
        tvReleaseYear.text = movie.releaseDate?.substring(0, 4) ?: "Unknown"
        tvTime.text = movie.getDurationInHourAndMinute()
        bindGenres(movie, movie.genres?: listOf())
        rbMovieDetailRating.rating = movie.getRatingBasedOnFiveStars()
        tvMovieRating.text = movie.voteAverage?.toString() ?: ""
        movie.voteCount?.let { voteCount ->
            tvVoteCount.text = "$voteCount VOTES"
        }
        tvOverview.text = movie.overview ?: ""
        tvOriginalTitle.text = movie.originalTitle ?: ""
        tvType.text = movie.getGenresAsCommaSeparatedString()
        tvProduction.text = movie.getProductionCountriesAsCommaSeparatedString()
        tvPremiere.text = movie.getReleaseDateInDMY()
        tvDescription.text = movie.overview ?: ""
    }

    private fun bindGenres(
        movie: MovieVO,
        genres: List<GenreVO>
    ){
        movie.genres?.count()?.let { genreCount ->
            tvFirstGenre.text = genres.firstOrNull()?.name?: ""
            tvSecondGenre.text = genres.getOrNull(1)?.name?: ""
            tvThirdGenre.text = genres.getOrNull(2)?.name?: ""

            if(genreCount < 3){
                tvThirdGenre.visibility = View.GONE
            } else if(genreCount < 2){
                tvSecondGenre.visibility = View.GONE
            }
        }
    }
}