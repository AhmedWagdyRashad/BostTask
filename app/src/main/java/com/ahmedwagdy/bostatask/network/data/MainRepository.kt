package com.ahmedwagdy.bostatask.network.data

import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.ahmedwagdy.bostatask.models.UserModel
import com.ahmedwagdy.bostatask.network.api.BaseApiResponse
import com.ahmedwagdy.bostatask.network.api.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class MainRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getUsers(): Flow<NetworkResult<List<UserModel?>?>> {
        return flow<NetworkResult<List<UserModel?>?>> {
            emit(safeApiCall {
                remoteDataSource.getUsers()
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAlbums(userId: Int): Flow<NetworkResult<List<AlbumModel?>?>> {
        return flow<NetworkResult<List<AlbumModel?>?>> {
            emit(safeApiCall {
                remoteDataSource.getAlbums(userId)
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPhotos(albumId: Int): Flow<NetworkResult<List<PhotoModel?>?>> {
        return flow<NetworkResult<List<PhotoModel?>?>> {
            emit(safeApiCall {
                remoteDataSource.getPhotos(albumId)
            })
        }.flowOn(Dispatchers.IO)
    }
}