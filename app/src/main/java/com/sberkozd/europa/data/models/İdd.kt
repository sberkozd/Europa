package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class İdd(
    @Json(name = "root")
    val root: String?,
    @Json(name = "suffixes")
    val suffixes: List<String?>?
) : Parcelable