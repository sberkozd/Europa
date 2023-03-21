package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Maps(
    @Json(name = "googleMaps")
    val googleMaps: String?,
    @Json(name = "openStreetMaps")
    val openStreetMaps: String?
) : Parcelable