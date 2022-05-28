package com.ahmedwagdy.bostatask.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.models.UserModel
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import com.ahmedwagdy.bostatask.network.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _users: MutableLiveData<NetworkResult<List<UserModel?>?>> = MutableLiveData()
    val users: LiveData<NetworkResult<List<UserModel?>?>> = _users

    private val _albums: MutableLiveData<NetworkResult<List<AlbumModel?>?>> = MutableLiveData()
    val albums: LiveData<NetworkResult<List<AlbumModel?>?>> = _albums

    fun getUsers() {
        viewModelScope.launch {
            mainRepository.getUsers().collect { values ->
                _users.value = values
            }
        }
    }

    fun getAlbums(userId: Int) {
        viewModelScope.launch {
            mainRepository.getAlbums(userId).collect { values ->
                _albums.value = values
            }
        }
    }
}