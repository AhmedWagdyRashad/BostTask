package com.ahmedwagdy.bostatask.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumModel(
    val userId: Int? = null,
    val id: Int? = null,
    val title: String? = null
): Parcelable
