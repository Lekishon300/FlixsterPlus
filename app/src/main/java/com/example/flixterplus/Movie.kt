package com.example.flixterplus

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single Movie from the MovieDB API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */
class Movie {

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String?=null

    //TODO bookImageUrl
    @SerializedName("poster_path")
    var MovieImageUrl: String?=null
}