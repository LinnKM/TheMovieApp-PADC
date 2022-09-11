package com.padc.themovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.padc.themovieapp.R
import com.padc.themovieapp.dummy.dummyGenreList
import com.padc.themovieapp.adapters.BannerAdapter
import com.padc.themovieapp.adapters.ShowCaseAdapter
import com.padc.themovieapp.data.models.MovieModel
import com.padc.themovieapp.data.models.MovieModelImpl
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.themovieapp.network.dataagents.MovieDataAgent
import com.padc.themovieapp.network.dataagents.RetrofitDataAgentImpl
import com.padc.themovieapp.viewpods.ActorListViewPod
import com.padc.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {
    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowCaseAdapter: ShowCaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mPopularActorsViewPod: ActorListViewPod

    // Model
    private var mMovieModel: MovieModel = MovieModelImpl

    // Data
    private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        setUpViewPods()
        setUpBannerViewPager()
        setUpListeners()
        setUpShowCaseRecyclerView()

        // Request Data
        requestData()
    }

    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mPopularActorsViewPod = vpPopularActors as ActorListViewPod
    }

    private fun setUpListeners() {

        // Genre TabLayout Listener
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mGenres?.getOrNull(tab?.position ?: 0)?.id?.let {
                    getMoviesByGenre(it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun requestData() {

        // Now Playing Movies
        mMovieModel.getNowPlayingMovies(
            onSuccess = {
                mBannerAdapter.setNewData(it)
            },
            onFailure = {
                // Print Error
            }
        )

        // Popular Movies
        mMovieModel.getPopularMovies(
            onSuccess = {
                mBestPopularMovieListViewPod.setData(it)
            },
            onFailure = {

            }
        )

        // Top Rated Movies
        mMovieModel.getTopRatedMovies(
            onSuccess = {
                mShowCaseAdapter.setNewData(it)
            },
            onFailure = {

            }
        )

        // Genre Tabs
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                setUpGenreTabLayout(it)
                // Movies By Genre
                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenre(genreId)
                }
            },
            onFailure = {

            }
        )

        // Actors
        mMovieModel.getActors(
            onSuccess = {
                mPopularActorsViewPod.setData(it)
            },
            onFailure = {

            }
        )

    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(this)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter

        dots_indicator.attachTo(viewPagerBanner)
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setUpGenreTabLayout(genres: List<GenreVO>) {
        genres.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun getMoviesByGenre(genreId: Int) {
        mMovieModel.getMoviesByGenre(
            genreId = genreId.toString(),
            onSuccess = {
                mMoviesByGenreViewPod.setData(it)
            },
            onFailure = {

            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSearch) {
            Toast.makeText(this, "This is search icon", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }
}