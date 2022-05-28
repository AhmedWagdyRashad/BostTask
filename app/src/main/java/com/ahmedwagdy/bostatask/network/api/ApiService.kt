package com.ahmedwagdy.bostatask.network.api

import com.ahmedwagdy.bostatask.models.AlbumModel
import com.ahmedwagdy.bostatask.models.PhotoModel
import com.ahmedwagdy.bostatask.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
        const val USERS = "/users"
        const val ALBUMS = "/albums"
        const val PHOTOS = "/photos"
    }

    @GET(USERS)
    suspend fun getUsers(): Response<List<UserModel?>?>

    @GET(ALBUMS)
    suspend fun getAlbums(
        @Query("userId") userId: Int
    ): Response<List<AlbumModel?>?>

    @GET(PHOTOS)
    suspend fun getPhotos(
        @Query("albumId") albumId: Int
    ): Response<List<PhotoModel?>?>
}