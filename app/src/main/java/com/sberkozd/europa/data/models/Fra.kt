package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Fra(
    @Json(name = "f")
    val f: String?,
    @Json(name = "m")
    val m: String?
) : Parcelable