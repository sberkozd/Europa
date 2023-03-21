package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Demonyms(
    @Json(name = "eng")
    val eng: Eng?,
    @Json(name = "fra")
    val fra: Fra?
) : Parcelable