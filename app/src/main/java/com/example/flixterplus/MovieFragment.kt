package com.example.flixterplus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

private const val API_KEY="a04b47141bec6c3570bcbf31d2bcdfbf"
/*
The class for the only fragment in the app, which contains the progress bar,
 recyclerView, and performs the network calls to the MovieDB API.
 */

class MovieFragment :Fragment(),OnListFragmentInteractionListener{

    /*
    constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list,container,false)
        val progressBar=view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar//casted to progressbar
        val recyclerView=view.findViewById<View>(R.id.list) as RecyclerView
        val context =view.context
        recyclerView.layoutManager=GridLayoutManager(context,1)
        updateAdapter(progressBar,recyclerView)
        return view
    }
    /*
      Updates the RecyclerView adapter with new data.  This is where the
      networking magic happens!
     */

    private fun updateAdapter(progressBar: ContentLoadingProgressBar,recyclerView: RecyclerView){
        progressBar.show()

        //create and set up an
        val client = AsyncHttpClient()
        val params=RequestParams()

        params["api_key"]= API_KEY

        Log.d("MovieFragment","testing updateAdapter")
        client["https://api.themoviedb.org/3/movie/now_playing",params,object:JsonHttpResponseHandler()
        {

            override fun onSuccess(statusCode: Int,
                                   headers: Headers?,
                                   json: JsonHttpResponseHandler.JSON) {
                //the wait for response time is over
                progressBar.hide()
                val resultsJSONArrayString=json.jsonObject.getJSONArray("results").toString()
                val gson= Gson()
                val type =object : TypeToken<List<Movie>>() {}.type
                val movies: List<Movie> = gson.fromJson(resultsJSONArrayString,type)

                recyclerView.adapter=MovieRecyclerViewAdapter(movies,this@MovieFragment)

                Log.d("MovieFragment","response successful")
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                progressBar.hide()

                //if the error is not null,log it!
                throwable?.message.let{
                    Log.e("MovieFragment",errorResponse)
                }
            }

        }]
    }
    /*
    what happens when a particular movie is clicked
     */
    override fun onItemClick(item: Movie) {
     //  Toast.makeText(context,"test: "+item.title,Toast.LENGTH_LONG).show()
    }

}