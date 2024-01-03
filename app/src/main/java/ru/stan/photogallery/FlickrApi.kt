package ru.stan.photogallery

import retrofit2.http.GET


private const val API_KEY = "a2ed5118fafd65ef9852df5bcb758bd6"

interface FlickrApi {
     @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchPhotos(): FlickrResponse
}