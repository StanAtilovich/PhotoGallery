package ru.stan.photogallery

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.stan.photogallery.api.FlickrApi
import ru.stan.photogallery.api.PhotoInterceptor


class PhotoRepository() {
    private val flickrApi: FlickrApi


    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    suspend fun fetchPhotos(page: Int): List<GalleryItem> =
        flickrApi.fetchPhotos(page).photos.galleryItems

    suspend fun searchPhotos(query: String): List<GalleryItem> =
    flickrApi.searchPhotos(query).photos.galleryItems
}