package com.padc.themovieapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.padc.themovieapp.R
import com.padc.themovieapp.adapters.BannerAdapter
import com.padc.themovieapp.adapters.ShowCaseAdapter
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.data.vos.GenreVO
import com.padc.themovieapp.data.vos.MovieVO
import com.padc.themovieapp.mvp.presenters.MainPresenter
import com.padc.themovieapp.mvp.presenters.MainPresenterImpl
import com.padc.themovieapp.mvp.views.MainView
import com.padc.themovieapp.viewpods.ActorListViewPod
import com.padc.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowCaseAdapter: ShowCaseAdapter
    private lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mPopularActorsViewPod: ActorListViewPod

    // Presenter
    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()

        setUpToolBar()
        setUpViewPods()
        setUpBannerViewPager()
        setUpListeners()
        setUpShowCaseRecyclerView()

        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mPopularActorsViewPod = vpPopularActors as ActorListViewPod
    }

    private fun setUpListeners() {

        // Genre TabLayout Listener
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showPopularMovies(popularMovies: List<MovieVO>) {
        mBestPopularMovieListViewPod.setData(popularMovies)
    }

    override fun showTopRateMovies(topRatedMovies: List<MovieVO>) {
        mShowCaseAdapter.setNewData(topRatedMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showMoviesByGenre(moviesByGenre: List<MovieVO>) {
        mMoviesByGenreViewPod.setData(moviesByGenre)
    }

    override fun showActors(actors: List<ActorVO>) {
        mPopularActorsViewPod.setData(actors)
    }

    override fun navigateToMovieDetailsScreen(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
    }

    override fun navigateToMovieSearchScreen() {
        startActivity(MovieSearchActivity.newIntent(this))
    }

    override fun navigateToMovieTrailer(key: String) {
        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=$key")
            )
        startActivity(intent)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(mPresenter)
        rvShowCases.adapter = mShowCaseAdapter
        rvShowCases.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(mPresenter)
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSearch) {
            mPresenter.onTapSearch()
        }
        return true
    }
}