package ru.stan.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel() : ViewModel() {
    private val photoRepository = PhotoRepository()


    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    private var currentPage = 1
    init {
        loadNextPage()
    }
    fun loadNextPage(){
        viewModelScope.launch {
            try {
                val items = photoRepository.fetchPhotos(currentPage)
                Log.d(TAG, "Items received: $items")
                val currentItems = _galleryItems.value.toMutableList()
                currentItems.addAll(items)
                _galleryItems.value = currentItems
                currentPage++
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }
}
