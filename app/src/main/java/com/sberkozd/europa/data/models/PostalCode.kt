package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PostalCode(
    @Json(name = "format")
    val format: String?,
    @Json(name = "regex")
    val regex: String?
) : Parcelable