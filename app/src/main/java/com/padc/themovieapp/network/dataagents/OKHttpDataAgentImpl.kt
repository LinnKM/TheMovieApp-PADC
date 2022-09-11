//package com.padc.themovieapp.network.dataagents
//
//import android.os.AsyncTask
//import com.google.gson.Gson
//import com.padc.themovieapp.data.vos.MovieVO
//import com.padc.themovieapp.network.responses.MovieListResponse
//import com.padc.themovieapp.utils.API_GET_NOW_PLAYING
//import com.padc.themovieapp.utils.BASE_URL
//import com.padc.themovieapp.utils.MOVIE_API_KEY
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import java.lang.Exception
//import java.util.concurrent.TimeUnit
//
//object OKHttpDataAgentImpl: MovieDataAgent{
//
//    private val mClient: OkHttpClient = OkHttpClient.Builder()
//        .connectTimeout(15, TimeUnit.SECONDS)
//        .readTimeout(15, TimeUnit.SECONDS)
//        .writeTimeout(15, TimeUnit.SECONDS)
//        .build()
//
//    class GetNowPlayingMovieTask(private val mOkHttpClient: OkHttpClient) : AsyncTask<Void, Void, MovieListResponse?>() {
//        override fun doInBackground(vararg params: Void?): MovieListResponse? {
//            val request = Request.Builder()
//                .url("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US""")
//                .build()
//
//            try{
//                val response = mOkHttpClient.newCall(request).execute()
//
//                if(response.isSuccessful){
//                    response.body?.let {
//                        val responseString = it.string()
//                        val response = Gson().fromJson(
//                            responseString,
//                            MovieListResponse::class.java
//                        )
//                        return response
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: MovieListResponse?) {
//            super.onPostExecute(result)
//        }
//
//    }
//
//    override fun getNowPlayingMovies(
//        onSuccess: (List<MovieVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        GetNowPlayingMovieTask(mClient)
//    }
//
//}