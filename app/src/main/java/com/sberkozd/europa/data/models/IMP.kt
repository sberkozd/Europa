package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class IMP(
    @Json(name = "name")
    val name: String?,
    @Json(name = "symbol")
    val symbol: String?
) : Parcelable