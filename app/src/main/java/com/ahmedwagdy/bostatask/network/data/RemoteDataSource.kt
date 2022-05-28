package com.ahmedwagdy.bostatask.network.data

import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.network.api.ApiService
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val userApiService: ApiService
){

    suspend fun getUsers(
    ) = userApiService.getUsers()

    suspend fun getAlbums(
        userId: Int
    ) = userApiService.getAlbums(userId)

    suspend fun getPhotos(
        albumId: Int
    ) = userApiService.getPhotos(albumId)
}