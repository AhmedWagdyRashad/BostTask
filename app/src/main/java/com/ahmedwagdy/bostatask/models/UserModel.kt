package com.ahmedwagdy.bostatask.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
val id: Int? = null,
val name: String? = null,
val address: Address? = null,
): Parcelable{
    @Parcelize
    data class Address(
        val street: String? = null,
        val suite: String? = null,
        val city: String? = null,
        val zipcode: String? = null,
    ): Parcelable
}
