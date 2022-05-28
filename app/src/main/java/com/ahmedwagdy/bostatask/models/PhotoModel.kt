package com.ahmedwagdy.bostatask.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoModel(
    val albumId: Int? = null,
    val id: Int? = null,
    val title: String? = null,
    val url: String? = null,
    val thumbnailUrl: String? = null
): Parcelable
