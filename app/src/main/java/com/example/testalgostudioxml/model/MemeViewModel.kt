package com.example.testalgostudioxml.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.http.Url

class MemeViewModel : ViewModel() {
    val getModelMim: LiveData<MutableList<ModelMim>>
    private val repository: MemeRepository

    init {
        repository = MemeRepository()
        getModelMim = repository.getAll()
    }

    suspend fun getImage(context: Context,url: String): Bitmap {

            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable

            val bitmap = (result as BitmapDrawable).bitmap
            return bitmap
    }

}