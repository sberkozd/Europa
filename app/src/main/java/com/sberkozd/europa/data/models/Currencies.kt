package com.sberkozd.europa.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Currencies(
    @Json(name = "ALL")
    val aLL: ALL?,
    @Json(name = "BAM")
    val bAM: BAM?,
    @Json(name = "BGN")
    val bGN: BGN?,
    @Json(name = "BYN")
    val bYN: BYN?,
    @Json(name = "CHF")
    val cHF: CHF?,
    @Json(name = "CZK")
    val cZK: CZK?,
    @Json(name = "DKK")
    val dKK: DKK?,
    @Json(name = "EUR")
    val eUR: EUR?,
    @Json(name = "FOK")
    val fOK: FOK?,
    @Json(name = "GBP")
    val gBP: GBP?,
    @Json(name = "GGP")
    val gGP: GGP?,
    @Json(name = "GIP")
    val gIP: GIP?,
    @Json(name = "HUF")
    val hUF: HUF?,
    @Json(name = "JEP")
    val jEP: JEP?,
    @Json(name = "MDL")
    val mDL: MDL?,
    @Json(name = "MKD")
    val mKD: MKD?,
    @Json(name = "NOK")
    val nOK: NOK?,
    @Json(name = "PLN")
    val pLN: PLN?,
    @Json(name = "RON")
    val rON: RON?,
    @Json(name = "RSD")
    val rSD: RSD?,
    @Json(name = "RUB")
    val rUB: RUB?,
    @Json(name = "SEK")
    val sEK: SEK?,
    @Json(name = "UAH")
    val uAH: UAH?,
    @Json(name = "IMP")
    val ıMP: IMP?,
    @Json(name = "ISK")
    val ıSK: ISK?
) : Parcelable