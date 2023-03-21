package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Car(
    @Json(name = "side")
    val side: String?,
    @Json(name = "signs")
    val signs: List<String?>?
) : Parcelable