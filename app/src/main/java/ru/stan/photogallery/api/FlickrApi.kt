package ru.stan.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.stan.photogallery.FlickrResponse
private const val API_KEY = "a2ed5118fafd65ef9852df5bcb758bd6"

interface FlickrApi {
//    @GET(
//        "services/rest/?method=flickr.interestingness.getList" +
//                "&api_key=$API_KEY" +
//                "&format=json" +
//                "&nojsoncallback=1" +
//                "&extras=url_s"
//    )
@GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(@Query("page") page: Int): FlickrResponse
//    suspend fun fetchPhotos(): FlickrResponse

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlickrResponse
}