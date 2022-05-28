package com.ahmedwagdy.bostatask.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import com.ahmedwagdy.bostatask.network.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _photosResponse: MutableLiveData<NetworkResult<List<PhotoModel?>?>> = MutableLiveData()
    val photosResponse: LiveData<NetworkResult<List<PhotoModel?>?>> = _photosResponse

    fun getPhotos(albumId: Int) {
        viewModelScope.launch {
            mainRepository.getPhotos(albumId).collect { values ->
                _photosResponse.value = values
            }
        }
    }
}