package com.example.flixterplus

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * [RecyclerView.Adapter] that can display a [BestSellerBook] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MovieRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.movieTitle.text = movie.title
        holder.movieOverview.text=movie.overview

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/"+movie.MovieImageUrl)
            .centerInside()
            .into(holder.movieImage)

        Log.d("movieImageUrl",  movie.MovieImageUrl +"")


        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val movieTitle: TextView = mView.findViewById(R.id.movie_title)
        val movieOverview: TextView = mView.findViewById(R.id.movie_description)
        val movieImage:ImageView=mView.findViewById(R.id.movie_image)

        override fun toString(): String {
            return movieTitle.toString() + " '" + movieOverview.text + "'"
        }
    }
}