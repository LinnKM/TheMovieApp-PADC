//package com.padc.themovieapp.network.dataagents
//
//import android.os.AsyncTask
//import android.util.Log
//import com.google.gson.Gson
//import com.padc.themovieapp.network.responses.MovieListResponse
//import com.padc.themovieapp.utils.API_GET_NOW_PLAYING
//import com.padc.themovieapp.utils.BASE_URL
//import com.padc.themovieapp.utils.MOVIE_API_KEY
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStreamReader
//import java.lang.Exception
//import java.lang.StringBuilder
//import java.net.URL
//import javax.net.ssl.HttpsURLConnection
//
//object MovieDataAgentImpl : MovieDataAgent {
//    override fun getNowPlayingMovies() {
//        GetNowPlayingMovieTask().execute()
//    }
//
//    class GetNowPlayingMovieTask() : AsyncTask<Void, Void, MovieListResponse?>() {
//        override fun doInBackground(vararg p0: Void?): MovieListResponse? {
//            val url: URL
//            var reader: BufferedReader? = null
//            val stringBuilder: StringBuilder
//
//            try {
//
//                url = URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US""")
//
//                val connection = url.openConnection() as HttpsURLConnection
//
//                connection.requestMethod = "GET"
//                connection.readTimeout = 20 * 1000
//                connection.doInput = true
//                connection.doOutput = false
//                connection.connect()
//
//                reader = BufferedReader(
//                    InputStreamReader(connection.inputStream)
//                )
//
//                stringBuilder = StringBuilder()
//
//                for (line in reader.readLines()) {
//                    stringBuilder.append(line + "\n")
//                }
//
//                val responseString = stringBuilder.toString()
//                Log.d("NowPlayingMovies", responseString)
//
//                val movieListResponse = Gson().fromJson(
//                    responseString,
//                    MovieListResponse::class.java
//                )
//                return movieListResponse
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.e("Error", e.message ?: "")
//            } finally {
//                if (reader != null){
//                    try{
//                        reader.close()
//                    } catch (ioe: IOException) {
//                        ioe.printStackTrace()
//                    }
//                }
//            }
//            return null
//        }
//
//    }
//}