package ru.stan.photogallery

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.stan.photogallery.api.FlickrApi
import ru.stan.photogallery.api.GalleryItem
import ru.stan.photogallery.api.PhotoInterceptor

class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
        flickrApi = retrofit.create()
    }

    suspend fun fetchPhotos(): List<GalleryItem> =
        flickrApi.fetchPhotos().photos.galleryItems

    suspend fun searchPhotos(query: String): List<GalleryItem> =
        flickrApi.searchPhotos(query).photos.galleryItems
}
