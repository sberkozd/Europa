package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Gini(
    @Json(name = "2011")
    val x2011: Double?,
    @Json(name = "2016")
    val x2016: Double?,
    @Json(name = "2017")
    val x2017: Double?,
    @Json(name = "2018")
    val x2018: Double?,
    @Json(name = "2019")
    val x2019: Double?
) : Parcelable