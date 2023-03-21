package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class CoatOfArms(
    @Json(name = "png")
    val png: String?,
    @Json(name = "svg")
    val svg: String?
) : Parcelable